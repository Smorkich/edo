package com.education.controller.employee;

import com.education.service.emloyee.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


/**
 * @author Kiladze George
 * Rest-контроллер отправляет запрос от клиента в бд
 */
@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Rest- контроллер для работы с сотрудниками")
@RequestMapping("/api/service/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Создает сотрудника")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto save(@RequestBody EmployeeDto employeeDto) {
        log.info("Starting the save operation");
        employeeService.save(employeeDto);
        log.info("Saving the employee");
        return employeeDto;
    }

    @Operation(summary = "Архивация сотрудника с занесением времени архивации")
    @PostMapping(value = "/{id}")
    public void moveToArchived(@PathVariable Long id) {
        log.info("Starting the archiving operation");
        employeeService.moveToArchived(id);
        log.info("Archiving the employee");
    }

    @Operation(summary = "Предоставление сотрудника по индентификатору")
    @GetMapping(value = "/{id}")
    public EmployeeDto findById(@PathVariable Long id) {
        log.info("Send a response with the employee of the assigned id");
        var employeeDto = employeeService.findById(id);
        log.info("The operation was successful, we got the employee by id ={}", id);
        return employeeDto;
    }

    @Operation(summary = "Предоставление всех сотрудников")
    @GetMapping(value = "/all")
    public Collection<EmployeeDto> findAll() {
        log.info("Send a response with the employees");
        var employeeDto = employeeService.findAll();
        log.info("The operation was successful, we got the all employees");
        return employeeDto;
    }

    @Operation(summary = "Предоставление сотрудников по назначеным идентификаторам")
    @GetMapping(value = "/all/{ids}")
    public Collection<EmployeeDto> findAllById(@PathVariable String ids) {
        log.info("Send a response with the employee of the assigned IDs");
        var employeeDto = employeeService.findAllById(ids);
        log.info("The operation was successful, we got the employee by id = {} ", ids);
        return employeeDto;
    }

    @Operation(summary = "Предоставление сотрудника без архивирования по идентификатору")
    @GetMapping(value = "/NotArchived/{id}")
    public EmployeeDto findByIdNotArchived(@PathVariable Long id) {
        log.info("Send a response with the employee not archived of the assigned ID");
        var employeeDto = employeeService.findByIdAndArchivedDateNull(id);
        log.info("The operation was successful, they got the non-archived employee by id ={}", id);
        return employeeDto;
    }

    @Operation(summary = "Предоставление сотрудников без архивирования по идентификатору")
    @GetMapping(value = "/NotArchivedAll/{ids}")
    public Collection<EmployeeDto> findByAllIdNotArchived(@PathVariable String ids) {
        log.info("Send a response with the employee not archived of the assigned IDs");
        var employeeDto = employeeService.findByIdInAndArchivedDateNull(ids);
        log.info("The operation was successful, they got the non-archived employee by id ={}", ids);
        return employeeDto;
    }

    /**
     * Сохраняет коллекцию сотрудников
     *
     * @param employeeDto - Коллекция сотрудников
     */
    @Operation(summary = "Сохраняет коллекцию сотрудников")
    @PostMapping(value = "/collection")
    public Collection<EmployeeDto> saveCollection(@RequestBody Collection<EmployeeDto> employeeDto) {
        log.info("Send a response with the collection employee");
        var collection = employeeService.saveCollection(employeeDto);
        log.info("The operation was successful, they saved the collection");
        return collection;
    }

    @Operation(summary = "Предоставление сотрудников по ФИО")
    @GetMapping("/search")
    public Collection<EmployeeDto> findByFullName(@RequestParam("fullName") String fullName) {
        log.info("Принимает полное имя {} на стороне edo-service", fullName);
        return employeeService.findAllByFullName(fullName);
    }
    @Operation(summary = "Предоставление сотрудника по email и username")
    @GetMapping("/searchByEmailAndUsername")
    public EmployeeDto findByEmailAndUsername(@RequestParam("email") String email,
                                              @RequestParam("username") String username) {
        log.info("Принимает username {} и email {} на стороне edo-service", username, email);
        return employeeService.findByEmailAndUsername(email, username);
    }

}
