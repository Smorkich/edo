package com.education.controller.employee;

import com.education.service.employee.impl.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.util.Collection;
import java.util.List;

import static com.education.mapper.EmployeeMapper.EMPLOYEE_MAPPER;


/**
 * @author George Kiladze
 * Rest-контроллер отправляет запрос от клиента в бд
 */
@Log4j2
@Tag(name = "Rest- контроллер для работы с сотрудниками")
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
    @Operation(summary = "Предоставление сотрудника по индентификатору")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> findById(@PathVariable Long id) {
        log.info("Send a response with the employee of the assigned id");
        EmployeeDto employeeDto = EMPLOYEE_MAPPER.toDto(employeeService.findById(id));
        log.info("The operation was successful, we got the employee by id = {}", id);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * предоставляет всех сотрудников
     */
    @Operation(summary = "Предоставление всех сотрудников")
    @GetMapping("/all")
    public ResponseEntity<Collection<EmployeeDto>> findAll() {
        log.info("Send a response with the employees");
        var employeeDto = EMPLOYEE_MAPPER.toDto(employeeService.findAll());
        log.info("The operation was successful, we got the all employees");
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * предоставляет сотрудников по нескольким id
     *
     * @param ids
     */
    @Operation(summary = "Предоставление сотрудников по назначеным идентификаторам")
    @GetMapping("/all/{ids}")
    public ResponseEntity<Collection<EmployeeDto>> findAllById(@PathVariable List<Long> ids) {
        log.info("Send a response with the employee of the assigned IDs");
        var employeeDto = EMPLOYEE_MAPPER.toDto(employeeService.findAllById(ids));
        log.info("The operation was successful, we got the employee by id = {} ", ids);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * добавляет сотрудника
     *
     * @param employeeDto
     */

    @Operation(summary = "Создает сотрудника")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto employeeDto) {
        log.info("Starting the save operation");
        employeeService.save(EMPLOYEE_MAPPER.toEntity(employeeDto));
        log.info("Saving the employee");
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    /**
     * архивация сотрудника с занесением времени архивации
     *
     * @param id
     */
    @Operation(summary = "Архивация сотрудника с занесением времени архивации")
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> moveToArchived(@PathVariable Long id) {
        log.info("Starting the archiving operation");
        employeeService.moveToArchived(id);
        log.info("Archiving the employee");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * предоставляет заархивированного сотрудника по id
     *
     * @param id
     */
    @Operation(summary = "Предоставление сотрудника без архивирования по идентификатору")
    @GetMapping(value = "/notArchived/{id}")
    public ResponseEntity<EmployeeDto> findByIdNotArchived(@PathVariable Long id) {
        log.info("Send a response with the employee not archived of the assigned ID");
        var employeeDto = EMPLOYEE_MAPPER.toDto(employeeService.findByIdAndArchivedDateNull(id));
        log.info("The operation was successful, they got the non-archived employee by id ={}", id);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * предоставляет заархивированных сотрудников по нескольким id
     *
     * @param ids
     */
    @Operation(summary = "Предоставление сотрудников без архивирования по идентификатору")
    @GetMapping(value = "/notArchivedAll/{ids}")
    public ResponseEntity<Collection<EmployeeDto>> findByAllIdNotArchived(@PathVariable List<Long> ids) {
        log.info("Send a response with the employee not archived of the assigned IDs");
        var employeeDto = EMPLOYEE_MAPPER.toDto(employeeService.findByIdInAndArchivedDateNull(ids));
        log.info("The operation was successful, they got the non-archived employee by id ={}", ids);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * Сохраняет коллекцию сотрудников
     *
     * @param employeeDto - Коллекция сотрудников
     */
    @Operation(summary = "Сохраняет коллекцию сотрудников")
    @PostMapping(value = "/collection")
    public ResponseEntity<Collection<EmployeeDto>> saveCollection(@RequestBody Collection<EmployeeDto> employeeDto) {
        log.info("Send a response with the collection employee");

        var collection = EMPLOYEE_MAPPER.toDto(employeeService.saveCollection(EMPLOYEE_MAPPER.toEntity(employeeDto)));

        log.info("The operation was successful, they saved the collection");
        return new ResponseEntity<>(collection, HttpStatus.OK);
    }


    /**
     * предоставляет сотрудников по ФИО
     *
     * @param fullName
     */
    @Operation(summary = "Предоставление сотрудников по ФИО")
    @GetMapping(value = "/search")
    public ResponseEntity<Collection<EmployeeDto>> findAllByFullName(@RequestParam("fullName") String fullName) {
        log.info("Send a response with the requested full name: {}", UriUtils.decode(fullName, "UTF-8"));
        var decodeFullName = UriUtils.decode(fullName, "UTF-8");
        log.info(decodeFullName);
        Collection<EmployeeDto> employeeDto = EMPLOYEE_MAPPER.toDto(employeeService.findAllByFullName(decodeFullName));
        log.info("The operation was successful, they got employee with full name ={}", decodeFullName);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * Нахождение сотрудника по email и username
     */
    @Operation(summary = "Нахождение сотрудника по email и username")
    @GetMapping(value = "/searchByEmailAndUsername")
    public ResponseEntity<EmployeeDto> findByEmailOrUsername(@RequestParam("email") String email,
                                                              @RequestParam("username") String username) {
        var decodeEmail = UriUtils.decode(email, "UTF-8");
        var decodeUsername = UriUtils.decode(username, "UTF-8");
        EmployeeDto employeeDto = EMPLOYEE_MAPPER.toDto(employeeService
                .findByEmailOrUsername(decodeEmail, decodeUsername));
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

}
