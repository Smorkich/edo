package com.education.controller;

import com.education.mapper.DepartmentMapper;
import com.education.service.department.DepartmentService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import model.dto.DepartmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("api/repository")
public class DepartmentController {
   private final DepartmentService departmentService;
   private static final  DepartmentMapper departmentMapper = DepartmentMapper.mapper;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;

    }

    /**
     * достает департамент по id
     * @param id
     * @return
     *
     */
    @ApiOperation(value = "Предоставление департамента по индентификатору")
    @GetMapping("/department/{id}")
    private ResponseEntity<DepartmentDto> getDepartment(@PathVariable(name = "id" ) Long id) {
        DepartmentDto department = departmentMapper.toDTO(departmentService.findById(id));
        return  new ResponseEntity<>(department, HttpStatus.OK);
    }

    /**
     * достает департамент по id и если у него нет даты архивации
     * @param id
     * @return
     *
     */
    @ApiOperation(value = "Предоставление департамента без архивации по идентификатору")
    @GetMapping("/department/NotArchived/{id}")
    private ResponseEntity<DepartmentDto> getDepartmentNotArchived(@PathVariable(name = "id" ) Long id) {
        DepartmentDto department = departmentMapper.toDTO(departmentService.findByIdNotArchived(id));
        return  new ResponseEntity<>(department, HttpStatus.OK);
    }

    /**
     * достает департаменты по id и если у них нет даты архивации
     * @param ids
     * @return
     *
     */
    @ApiOperation(value = "Предоставление дапартаментов без архивации по назначеным идентификаторам")
    @GetMapping("/department/NotArchivedAll/{ids}")
    private  ResponseEntity<List<DepartmentDto>> getDepartmentsNotArchived(@PathVariable(name = "ids") List <Long> ids) {
        List<DepartmentDto> departments = departmentService.findByAllIdNotArchived(ids).stream().map(departmentMapper::toDTO).toList();
        return  new ResponseEntity<>(departments,HttpStatus.OK);
    }

    /**
     * достает департаменты по нескольким id
     * @param ids
     * @return
     */
    @ApiOperation(value ="Предоставление дапартаментов  по назначеным идентификаторам" )
    @GetMapping("/department/all/{ids}")
    private ResponseEntity<List<DepartmentDto>> getDepartments(@PathVariable(name = "ids") List<Long> ids) {
        List<DepartmentDto> departments = departmentService.findByAllId(ids).stream().map(departmentMapper::toDTO).toList();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    /**
     * добавляет департамент
     * @param departmentDto
     * @return
     */
    @ApiOperation(value = "Добавлнение департамента")
    @PostMapping("/department")
    private ResponseEntity<String> saveDepartment(@RequestBody DepartmentDto departmentDto) {
        departmentService.save(departmentMapper.toDep(departmentDto));
        return new ResponseEntity<>("Added the department to the database",HttpStatus.OK);
    }

    /**
     * заносит дату архиваци в департамент, обозначая тем самым, архивацию
     * @param id
     * @return
     */
    @ApiOperation(value = "Архивация департамента с занесением времени архивации")
    @PostMapping("/department/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        departmentService.removeToArchived(id);
        return  new ResponseEntity<>("The department is archived",HttpStatus.OK);
    }

}
