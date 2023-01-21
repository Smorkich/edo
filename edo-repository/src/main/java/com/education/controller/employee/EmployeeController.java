package com.education.controller.employee;

import com.education.entity.Employee;
import com.education.service.employee.impl.EmployeeServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * @author George Kiladze
 * Rest-контроллер отправляет запрос от клиента в бд
 */
@Log4j2
@ApiOperation(value = "Контроллер сотрудника")
@RestController
@RequestMapping("/api/repository/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    /**
     * предоставляет сотрудника по id
     *
     * @param id
     */
    @ApiOperation(value = "Предоставление сотрудника по индентификатору")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> findById(@PathVariable Long id) {
        log.info("Send a response with the employee of the assigned id");
        EmployeeDto employeeDto = toDto(employeeService.findById(id));
        log.info("The operation was successful, we got the employee by id = {}", id);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * предоставляет всех сотрудников
     */
    @ApiOperation(value = "Предоставление сотрудников по назначеным идентификаторам")
    @GetMapping("/all")
    public ResponseEntity<Collection<EmployeeDto>> findAll() {
        log.info("Send a response with the employees");
        Collection<EmployeeDto> employeeDto = toDto(employeeService.findAll());
        log.info("The operation was successful, we got the all employees");
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * предоставляет сотрудников по нескольким id
     *
     * @param ids
     */
    @ApiOperation(value = "Предоставление сотрудников по назначеным идентификаторам")
    @GetMapping("/all/{ids}")
    public ResponseEntity<Collection<EmployeeDto>> findAllById(@PathVariable List<Long> ids) {
        log.info("Send a response with the employee of the assigned IDs");
        Collection<EmployeeDto> employeeDto = toDto(employeeService.findAllById(ids));
        log.info("The operation was successful, we got the employee by id = {} ", ids);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * добавляет сотрудника
     *
     * @param employeeDto
     */
    @ApiOperation(value = "Создает сотрудника")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto employeeDto) {
        log.info("Starting the save operation");
        employeeService.save(toEntity(employeeDto));
        log.info("Saving the employee");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * архивация сотрудника с занесением времени архивации
     *
     * @param id
     */
    @ApiOperation(value = "Архивация сотрудника с занесением времени архивации")
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> moveToArchived(@PathVariable Long id) {
        log.info("Starting the archiving operation");
        employeeService.moveToArchived(id);
        log.info("Archiving the employee");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * предоставляет заархивированного сторудника по id
     *
     * @param id
     */
    @ApiOperation(value = "Предоставление сотрудника без архивирования по идентификатору")
    @GetMapping(value = "/notArchived/{id}")
    public ResponseEntity<EmployeeDto> findByIdNotArchived(@PathVariable Long id) {
        log.info("Send a response with the employee not archived of the assigned ID");
        EmployeeDto employeeDto = toDto(employeeService.findByIdAndArchivedDateNull(id));
        log.info("The operation was successful, they got the non-archived employee by id ={}", id);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * предоставляет заархивированных сторудников по нескольким id
     *
     * @param ids
     */
    @ApiOperation(value = "Предоставление сотрудников без архивирования по идентификатору")
    @GetMapping(value = "/notArchivedAll/{ids}")
    public ResponseEntity<Collection<EmployeeDto>> findByAllIdNotArchived(@PathVariable List<Long> ids) {
        log.info("Send a response with the employee not archived of the assigned IDs");
        Collection<EmployeeDto> employeeDto = employeeService.findByIdInAndArchivedDateNull(ids).stream().map(this::toDto).toList();
        log.info("The operation was successful, they got the non-archived employee by id ={}", ids);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * предоставляет сотрудников по ФИО
     *
     * @param fullName
     */
    @ApiOperation(value = "Предоставление сотрудников по ФИО")
    @GetMapping(value = "/search")
    public ResponseEntity<Collection<EmployeeDto>> findAllByFullName(@RequestParam("fullName") String fullName) {
        log.info("Send a response with the requested full name");
        Collection<EmployeeDto> employeeDto = toDto(employeeService.findAllByFullName(fullName));
        log.info("The operation was successful, they got employee with full name ={}", fullName);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    public EmployeeDto toDto(Employee employee) {

        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .middleName(employee.getMiddleName())
                .address(employee.getAddress())
                .fioDative(employee.getFioDative())
                .fioNominative(employee.getFioNominative())
                .fioGenitive(employee.getFioGenitive())
                .externalId(employee.getExternalId())
                .phone(employee.getPhone())
                .workPhone(employee.getWorkPhone())
                .birthDate(employee.getBirthDate())
                .username(employee.getUsername())
                .creationDate(employee.getCreationDate())
                .archivedDate(employee.getArchivedDate())
                .build();

    }

    public List<EmployeeDto> toDto(Collection<Employee> employee) {
        return employee.stream().map(this::toDto).toList();
    }

    public Employee toEntity(EmployeeDto employeeDto) {

        return Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .middleName(employeeDto.getMiddleName())
                .address(employeeDto.getAddress())
                .fioDative(employeeDto.getFioDative())
                .fioNominative(employeeDto.getFioNominative())
                .fioGenitive(employeeDto.getFioGenitive())
                .externalId(employeeDto.getExternalId())
                .phone(employeeDto.getPhone())
                .workPhone(employeeDto.getWorkPhone())
                .birthDate(employeeDto.getBirthDate())
                .username(employeeDto.getUsername())
                .creationDate(employeeDto.getCreationDate())
                .archivedDate(employeeDto.getArchivedDate())
                .build();

    }

}
