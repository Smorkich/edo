package com.education.controller.employee;


import com.education.service.employee.EmployeeService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * @author Andrey Kryukov
 * Rest-контроллер отправляет запрос от клиента в сервисы
 */
@Log4j2
@ApiOperation("Контроллер сотрудника")
@RestController
@RequestMapping("/api/rest/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @ApiOperation(value = "Создает сотрудника")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto employeeDto) {
        log.info("Send a response to save a new employee = {}", employeeDto);
        employeeService.save(employeeDto);
        log.info("The employee was successfully saved");
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Предоставление сотрудников по ФИО")
    @GetMapping(value = "/search")
    public ResponseEntity<Collection<EmployeeDto>> findAllByFullName(@RequestParam("fullName") String fullName) {
        log.info("Send a response with the requested full name: {}", fullName);
        Collection<EmployeeDto> employeeDto = employeeService.findAllByFullName(fullName);
        log.info("The operation was successful, they got employee with full name = {}", fullName);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }
}
