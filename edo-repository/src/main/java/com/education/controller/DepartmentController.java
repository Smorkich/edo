package com.education.controller;

import com.education.mapper.DepartmentMapper;
import com.education.service.department.DepartmentService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import model.dto.DepartmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Usolkin Dmitry
 * Контроллер в модуле edo-repository,работате с моделью DepartmentDto,
 * которая произошла из сущности Department выполняет несколько операций:
 * добавляет департамент
 * заносит дату архиваци в департамент, обозначая тем самым, архивацию
 * достает департамент по id
 * достает департаменты по нескольким id
 * достает департамент по id и если у него нет даты архивации
 * достает департаменты по id и если у них нет даты архивации
 */

@RestController
@ApiOperation(value = "Контроллер департамента")
@AllArgsConstructor
@Log4j2
@RequestMapping("api/repository")
public class DepartmentController {
    private final DepartmentService departmentService;
    private static final DepartmentMapper departmentMapper = DepartmentMapper.mapper;

    /**
     * достает департамент по id
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Предоставление департамента по индентификатору")
    @GetMapping("/department/{id}")
    private ResponseEntity<DepartmentDto> getDepartment(@PathVariable(name = "id") Long id) {
        log.info("send a response with the department of the assigned id");
        DepartmentDto department = departmentMapper.toDTO(departmentService.findById(id));
        log.info("The operation was successful, we got the department by id ={}",id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    /**
     * достает департамент по id и если у него нет даты архивации
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Предоставление департамента без архивации по идентификатору")
    @GetMapping("/department/NotArchived/{id}")
    private ResponseEntity<DepartmentDto> getDepartmentNotArchived(@PathVariable(name = "id") Long id) {
        log.info("send a response with the department not archived of the assigned ID");
        DepartmentDto department = departmentMapper.toDTO(departmentService.findByIdNotArchived(id));
        log.info("The operation was successful, they got the non-archived department by id ={}",id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    /**
     * достает департаменты по id и если у них нет даты архивации
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "Предоставление дапартаментов без архивации по назначеным идентификаторам")
    @GetMapping("/department/NotArchivedAll/{ids}")
    private ResponseEntity<List<DepartmentDto>> getDepartmentsNotArchived(@PathVariable(name = "ids") List<Long> ids) {
        log.info("send a response with the departments not archived of the assigned IDs");
        List<DepartmentDto> departments = departmentService.findByAllIdNotArchived(ids).stream().map(departmentMapper::toDTO).toList();
        log.info("The operation was successful, they got the non-archived department by id ={}",ids);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    /**
     * достает департаменты по нескольким id
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "Предоставление дапартаментов  по назначеным идентификаторам")
    @GetMapping("/department/all/{ids}")
    private ResponseEntity<List<DepartmentDto>> getDepartments(@PathVariable(name = "ids") List<Long> ids) {
        log.info("send a response with the departments of the assigned IDs");
        List<DepartmentDto> departments = departmentService.findByAllId(ids).stream().map(departmentMapper::toDTO).toList();
        log.info("The operation was successful, we got the departments by id = {} ",ids);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    /**
     * добавляет департамент
     *
     * @param departmentDto
     * @return
     */
    @ApiOperation(value = "Добавлнение департамента")
    @PostMapping(value = "/department")
    private ResponseEntity<String> saveDepartment(@RequestBody DepartmentDto departmentDto) {
        System.out.println(departmentDto);
        log.info("Starting the save operation");
        System.out.println(departmentMapper.toDep(departmentDto));
        departmentService.save(departmentMapper.toDep(departmentDto));
        log.info("saving the department and displaying its full name in the response");
        return new ResponseEntity<>("Added the department to the database", HttpStatus.OK);
    }

    /**
     * заносит дату архиваци в департамент, обозначая тем самым, архивацию
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Архивация департамента с занесением времени архивации")
    @PostMapping("/department/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        log.info("Starting the archiving operation");
        departmentService.removeToArchived(id);
        log.info("Archiving a department");
        return new ResponseEntity<>("The department is archived", HttpStatus.OK);
    }

}
