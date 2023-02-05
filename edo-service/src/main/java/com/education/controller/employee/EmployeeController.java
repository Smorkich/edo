package com.education.controller.employee;

import com.education.service.emloyee.EmployeeService;
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

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;


/**
 * @author Kiladze George
 * Rest-контроллер отправляет запрос от клиента в бд
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/service/employee")
@ApiOperation("EmployeeController in module edo - service ")
public class EmployeeController {

    private final EmployeeService employeeService;

    @ApiOperation(value = "Создает сотрудника")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto employeeDto) {
        log.info("Starting the save operation");
        employeeService.save(employeeDto);
        log.info("Saving the employee");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Архивация сотрудника с занесением времени архивации")
    @PostMapping(value = "/{id}")
    public ResponseEntity<Void> moveToArchived(@PathVariable Long id) {
        log.info("Starting the archiving operation");
        employeeService.moveToArchived(id);
        log.info("Archiving the employee");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Предоставление сотрудника по индентификатору")
    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable Long id) {
        log.info("Send a response with the employee of the assigned id");
        EmployeeDto employeeDto = employeeService.findById(id);
        log.info("The operation was successful, we got the employee by id ={}", id);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Предоставление всех сотрудников")
    @GetMapping(value = "/all")
    public ResponseEntity<Collection<EmployeeDto>> findAll() {
        log.info("Send a response with the employees");
        Collection<EmployeeDto> employeeDto = employeeService.findAll();
        log.info("The operation was successful, we got the all employees");
        return new ResponseEntity<Collection<EmployeeDto>>(employeeDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Предоставление сотрудников по назначеным идентификаторам")
    @GetMapping(value = "/all/{ids}")
    public ResponseEntity<Collection<EmployeeDto>> findAllById(@PathVariable String ids) {
        log.info("Send a response with the employee of the assigned IDs");
        Collection<EmployeeDto> employeeDto = employeeService.findAllById(ids);
        log.info("The operation was successful, we got the employee by id = {} ", ids);
        return new ResponseEntity<Collection<EmployeeDto>>(employeeDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Предоставление сотрудника без архивирования по идентификатору")
    @GetMapping(value = "/NotArchived/{id}")
    public ResponseEntity<EmployeeDto> findByIdNotArchived(@PathVariable Long id) {
        log.info("Send a response with the employee not archived of the assigned ID");
        EmployeeDto employeeDto = employeeService.findByIdAndArchivedDateNull(id);
        log.info("The operation was successful, they got the non-archived employee by id ={}", id);
        return new ResponseEntity<EmployeeDto>(employeeDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Предоставление сотрудников без архивирования по идентификатору")
    @GetMapping(value = "/NotArchivedAll/{ids}")
    public ResponseEntity<Collection<EmployeeDto>> findByAllIdNotArchived(@PathVariable String ids) {
        log.info("Send a response with the employee not archived of the assigned IDs");
        Collection<EmployeeDto> employeeDto = employeeService.findByIdInAndArchivedDateNull(ids);
        log.info("The operation was successful, they got the non-archived employee by id ={}", ids);
        return new ResponseEntity<Collection<EmployeeDto>>(employeeDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Предоставление сотрудников по ФИО")
    @GetMapping(value = "/search")
    public ResponseEntity<Collection<EmployeeDto>> findAllByFullName(@RequestParam("fullName") String fullName) {
        if (fullName.length() < 3) {
            log.info("Send empty collection, characters less than 3");
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        log.info("Send a response with the requested full name");
        Collection<EmployeeDto> employeeDto = employeeService.findAllByFullName(fullName);
        log.info("The operation was successful, they got employee with full name ={}", fullName);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }
}
