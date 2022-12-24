package com.education.controller.appeal;

import com.education.entity.Appeal;
import com.education.entity.Employee;
import com.education.service.appeal.AppealService;
import com.education.service.employee.EmployeeService;
import lombok.AllArgsConstructor;
import model.dto.AppealDto;
import model.dto.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/api/repository/appeal")
public class AppealRestController {

    private AppealService appealService;

    private EmployeeService employeeService;

//    @PatchMapping(value = "{/id}")
//    public ResponseEntity<AppealDto> moveToArchive(@PathVariable Long id, AppealDto appealDto) {
//        appealService.moveToArchive(appealDto.getId(), appealDto.getArchivedDate());
//        return new ResponseEntity<>(appealDto ,HttpStatus.OK);
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AppealDto>> getAllAppeal() {
        var appealDtoCollection = entityAppealToDto(appealService.findAll());
        return new ResponseEntity<>(appealDtoCollection, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> deleteAppeal(@PathVariable(value = "id") Long id) {
        appealService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> saveAppeal(@RequestBody AppealDto appealDto) {
        appealService.save(toEntity(appealDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> getAppealById(@PathVariable Long id) {
        return new ResponseEntity<>(toDto(appealService.findById(id)), HttpStatus.OK);
    }

    /**
     * Маппинг из Appeal в Dto
     */
    public AppealDto toDto(Appeal appeal) {
        return new AppealDto(
                appeal.getId(),
                appeal.getCreationDate(),
                appeal.getArchivedDate(),
                appeal.getNumber(),
                appeal.getAnnotation(),
                toDto(appeal.getSigner()),
                toDto(appeal.getCreator()),
                toDto(appeal.getAddressee())
        );
    }

    /**
     * Маппинг из Collection<Appeal> в Dto
     */
    public Collection<AppealDto> entityAppealToDto(Collection<Appeal> appeals) {
        return appeals.stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Маппинг из AppealDto в Entity
     */
    public Appeal toEntity(AppealDto appealDto) {
        return new Appeal(
                appealDto.getCreationDate(),
                appealDto.getArchivedDate(),
                appealDto.getNumber(),
                appealDto.getAnnotation(),
                dtoEmployeeToEntity(appealDto.getSigner()),
                toEntity(appealDto.getCreator()),
                dtoEmployeeToEntity(appealDto.getAddressee())
        );
    }

    /**
     * Маппинг из Employee в Dto (Для полей AppealDto, содержащих EmployeeDto)
     */
    public EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getMiddleName(),
                employee.getAddress(),
                employee.getFioDative(),
                employee.getFioNominative(),
                employee.getFioGenitive(),
                employee.getExternalId(),
                employee.getPhone(),
                employee.getWorkPhone(),
                employee.getBirthDate(),
                employee.getUsername(),
                employee.getCreationDate(),
                employee.getArchivedDate()
        );
    }

    /**
     * Маппинг из Collection<Employee> в Dto (Для полей AppealDto, содержащих Collection<EmployeeDto>)
     */
    public Collection<EmployeeDto> toDto(Collection<Employee> employees) {
        return employees.stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Следующие 2 поля нужны, чтобы при подаче на POST-контроллер нового EmployeeDto
     * иметь возможность распарсить EmployeeDto в Employee, которое будет содержать id.
     * Id нужен, чтобы не создавать нового работника при создании каждого нового обращения,
     * а назначать из уже имеющихся.
     * <p>
     * <p>
     * Маппинг из EmployeeDto в Employee (Для POST-контроллера Appeal)
     */
    public Employee toEntity(EmployeeDto employeeDto) {
        return employeeService.findById(employeeDto.getId());
    }

    /**
     * Маппинг из Collection<EmployeeDto> в Collection<Employee> (Для POST-контроллера Appeal)
     */
    public Collection<Employee> dtoEmployeeToEntity(Collection<EmployeeDto> employees) {
        return employees.stream()
                .map(this::toEntity)
                .toList();
    }

}
