package com.education.controller;

import com.education.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import model.dto.DepartmentDto;
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
import java.util.logging.Logger;

/**
 *@author Usolkin Dmitry
 */
@RestController
@RequestMapping("api/service")
@ApiOperation("DepartmentController in module edo - service ")
public class DepartmentController {
    private static  final Logger LOGGER = Logger.getLogger(DepartmentController.class.getName());
    private final DepartmentService departmentService;


    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;

    }
    @ApiOperation(value = "Предоставление департамента по индентификатору")
    @GetMapping(value = "/department/{id}")
    private ResponseEntity<DepartmentDto> getDepartment(@PathVariable(name = "id" ) Long id) {
        LOGGER.info("send a response with the department of the assigned ID");
        return new ResponseEntity<>(departmentService.findById(id), HttpStatus.OK);

    }
    @ApiOperation(value = "Предоставление департамента без архивации по идентификатору")
    @GetMapping("/department/NotArchived/{id}")
    private ResponseEntity<DepartmentDto> getDepartmentNotArchived(@PathVariable(name = "id" ) Long id) {
       LOGGER.info("send a response with the department not archived of the assigned ID");
       return new ResponseEntity<>(departmentService.findByIdNotArchived(id),HttpStatus.OK);
    }
    @ApiOperation(value = "Предоставление дапартаментов без архивации по назначеным идентификаторам")
    @GetMapping("/department/NotArchivedAll/{ids}")
    private  ResponseEntity<List<DepartmentDto>> getDepartmentsNotArchived(@PathVariable(name = "ids") String ids) {
        LOGGER.info("send a response with the departments not archived of the assigned IDs");
        return new ResponseEntity<>(departmentService.findByAllIdNotArchived(ids), HttpStatus.OK);
    }
    @ApiOperation(value ="Предоставление дапартаментов  по назначеным идентификаторам" )
    @GetMapping("/department/all/{ids}")
    private ResponseEntity<List<DepartmentDto>> getDepartments(@PathVariable(name = "ids") String ids ) {
       LOGGER.info("send a response with the departments of the assigned IDs");
       return new ResponseEntity<>( departmentService.findByAllId(ids), HttpStatus.OK);
    }
    @ApiOperation(value = "Добавлнение департамента")
    @PostMapping(value = "/department")
    private String saveDepartment(@RequestBody DepartmentDto departmentDto) {
         departmentService.save(departmentDto);
         LOGGER.info("saving the department and displaying its full name in the response");
         return "Добавили " + departmentDto.getFullName();
    }
    @ApiOperation(value = "Архивация департамента с занесением времени архивации")
    @PostMapping("/department/{id}")
    private String deleteUser(@PathVariable(name = "id") Long id) {
        departmentService.removeToArchived(id);
        LOGGER.info("Deleting a department");
        return "Удалил " + id;
    }

}
