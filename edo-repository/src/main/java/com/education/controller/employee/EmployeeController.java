package com.education.controller.employee;

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
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static com.education.mapper.EmployeeMapper.EMPLOYEE_MAPPER;


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
        EmployeeDto employeeDto = EMPLOYEE_MAPPER.toDto(employeeService.findById(id));
        log.info("The operation was successful, we got the employee by id ={}", id);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * предоставляет всех сотрудников
     */
    @ApiOperation(value = "Предоставление сотрудников по назначеным идентификаторам")
    @GetMapping("/all")
    public ResponseEntity<Collection<EmployeeDto>> findAll() {
        log.info("Send a response with the employees");
        Collection<EmployeeDto> employeeDto = EMPLOYEE_MAPPER.toDto(employeeService.findAll());
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
        Collection<EmployeeDto> employeeDto = EMPLOYEE_MAPPER.toDto(employeeService.findAllById(ids));
        log.info("The operation was successful, we got the employee by id = {} ", ids);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * добавляет сотрудника
     *
     * @param employeeDto
     */
    @ApiOperation(value = "Создает сотрудника")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto employeeDto) {
        log.info("Starting the save operation");
        Long id = employeeService.save(EMPLOYEE_MAPPER.toEntity(employeeDto));
        log.info("Assigned to employee id={}", id);
        EmployeeDto employeeDto1 = EMPLOYEE_MAPPER.toDto(employeeService.findById(id));
        log.info("Saving the employee");
        return new ResponseEntity<>(employeeDto1, HttpStatus.CREATED);
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
        EmployeeDto employeeDto = EMPLOYEE_MAPPER.toDto(employeeService.findByIdAndArchivedDateNull(id));
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
        Collection<EmployeeDto> employeeDto = EMPLOYEE_MAPPER.toDto(employeeService.findByIdInAndArchivedDateNull(ids));
        log.info("The operation was successful, they got the non-archived employee by id ={}", ids);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    /**
     * Сохраняет коллекцию сотрудников
     *
     * @param employeeDto - Коллекция сотрудников
     */
    @ApiOperation(value = "Сохраняет коллекцию сотрудников")
    @PostMapping(value = "/collection")
    public ResponseEntity<Collection<EmployeeDto>> saveCollection(@RequestBody Collection<EmployeeDto> employeeDto) {
        log.info("Send a response with the collection employee");
        Collection<EmployeeDto> collection = EMPLOYEE_MAPPER.toDto(employeeService.saveCollection(EMPLOYEE_MAPPER.toEntity(employeeDto)));
        log.info("The operation was successful, they saved the collection");
        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

}
