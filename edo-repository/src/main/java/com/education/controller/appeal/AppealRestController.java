package com.education.controller.appeal;

import com.education.service.employee.EmployeeService;
import com.education.entity.Appeal;
import com.education.entity.Employee;
import com.education.service.appeal.AppealService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import model.dto.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.time.ZonedDateTime;

/**
 * Rest-контроллер для сущности Appeal
 */

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/repository/appeal")
public class AppealRestController {

    private EmployeeService employeeService;
    private AppealService appealService;


    @ApiOperation(value = "В строке таблицы Appeal заполняет поле archivedDate", notes = "Строка в Appeal должна существовать")
    @PatchMapping("/move/{id}")
    public ResponseEntity<AppealDto> moveToArchive(@PathVariable Long id) {
        log.info("Adding archived date {} in Appeal with id: {}", ZonedDateTime.now(), id);
        appealService.moveToArchive(id, ZonedDateTime.now());
        log.info("Moving appeal with id: {} to archive is success!", id);
        return new ResponseEntity<>(toDto(appealService.findById(id)), HttpStatus.OK);
    }
    @ApiOperation(value = "Находит все строки таблицы Appeal с полем acrhivedDate = null", notes = "Строка в Appeal должна существовать")
    @GetMapping(value = "/findAllNotArchived", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AppealDto>> findAllNotArchived() {
        log.info("Getting from database all appeals with field acrhivedDate = null");
        var appealDtoCollection = entityAppealToDto(appealService.findAllNotArchived());
        log.info("Appeals: {}", appealDtoCollection);
        return new ResponseEntity<>(appealDtoCollection, HttpStatus.OK);
    }
    @ApiOperation(value = "Находит строку таблицы Appeal c полем acrhivedDate = null, по заданному id", notes = "Строка в Appeal должна существовать")
    @GetMapping(value = "/findByIdNotArchived/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> findByIdNotArchived(@PathVariable Long id) {
        log.info("Getting from database appeal with field acrhivedDate = null, with id: {}", id);
        var appeal = appealService.findByIdNotArchived(id);
        log.info("Appeal: {}", appeal);
        return new ResponseEntity<>(toDto(appeal), HttpStatus.OK);
    }
    @ApiOperation(value = "Находит все строки таблицы Appeal", notes = "Строка в Appeal должна существовать")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AppealDto>> getAllAppeal() {
        log.info("Getting from database all appeals");
        var appealDtoCollection = entityAppealToDto(appealService.findAll());
        log.info("Appeals: {}", appealDtoCollection);
        return new ResponseEntity<>(appealDtoCollection, HttpStatus.OK);
    }
    @ApiOperation(value = "Удаляет строку таблицы Appeal по id", notes = "Строка в Appeal должна существовать")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> deleteAppeal(@PathVariable(value = "id") Long id) {
        log.info("Deleting from database appeal with id: {}", id);
        appealService.delete(id);
        log.info("Deleting from database appeal with id: {}, success!", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
    @ApiOperation(value = "Добавляет новую строку таблицы Appeal")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> saveAppeal(@RequestBody AppealDto appealDto) {
        log.info("Creating appeal");
        appealService.save(toEntity(appealDto));
        log.info("Creating appeal {}, success!", appealDto.getAnnotation());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @ApiOperation(value = "Находит строку таблицы Appeal по id", notes = "Строка в Appeal должна существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> getAppealById(@PathVariable Long id) {
        log.info("Getting from database appeal with id: {}", id);
        var appeal = appealService.findById(id);
        log.info("Appeal: {}", appeal);
        return new ResponseEntity<>(toDto(appeal), HttpStatus.OK);
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
