package com.education.controller.department;

import com.education.service.department.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.DepartmentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.education.mapper.DepartmentMapper.DEPARTMENT_MAPPER;

/**
 * @author Usolkin Dmitry
 * Контроллер в модуле edo-repository работает с моделью DepartmentDto,
 * которая произошла из сущности Department выполняет несколько операций:
 * добавляет департамент,
 * заносит дату архиваци в департамент, обозначая тем самым, архивацию
 * достает департамент по id
 * достает департаменты по нескольким id
 * достает департамент по id и если у него нет даты архивации
 * достает департаменты по id и если у них нет даты архивации
 */

@RestController
@Tag(name = "Rest- контроллер для работы с департаментами")
@AllArgsConstructor
@Log4j2
@RequestMapping("api/repository/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    /**
     * достает департамент по id
     *
     * @param id
     * @return
     */
    @Operation(summary = "Предоставление департамента по индентификатору")
    @GetMapping("/{id}")
    private ResponseEntity<DepartmentDto> getDepartment(@PathVariable(name = "id") Long id) {
        log.info("send a response with the department of the assigned id");
        DepartmentDto department = DEPARTMENT_MAPPER.toDto(departmentService.findById(id));
        log.info("The operation was successful, we got the department by id ={}",id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    /**
     * достает департамент по id и если у него нет даты архивации
     *
     * @param id
     * @return
     */
    @Operation(summary = "Предоставление департамента без архивации по идентификатору")
    @GetMapping("/notArchived/{id}")
    private ResponseEntity<DepartmentDto> getDepartmentNotArchived(@PathVariable(name = "id") Long id) {
        log.info("send a response with the department not archived of the assigned ID");
        DepartmentDto department = DEPARTMENT_MAPPER.toDto(departmentService.findByIdNotArchived(id));
        log.info("The operation was successful, they got the non-archived department by id ={}",id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    /**
     * достает департаменты по id и если у них нет даты архивации
     *
     * @param ids
     * @return
     */
    @Operation(summary = "Предоставление дапартаментов без архивации по назначеным идентификаторам")
    @GetMapping("/notArchivedAll/{ids}")
    private ResponseEntity<List<DepartmentDto>> getDepartmentsNotArchived(@PathVariable(name = "ids") List<Long> ids) {
        log.info("send a response with the departments not archived of the assigned IDs");
        List<DepartmentDto> departments =(List<DepartmentDto>) DEPARTMENT_MAPPER.toDto(departmentService.findByAllIdNotArchived(ids));
        log.info("The operation was successful, they got the non-archived department by id ={}",ids);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    /**
     * достает департаменты по нескольким id
     *
     * @param ids
     * @return
     */
    @Operation(summary = "Предоставление дапартаментов  по назначеным идентификаторам")
    @GetMapping("/all/{ids}")
    private ResponseEntity<List<DepartmentDto>> getDepartments(@PathVariable(name = "ids") List<Long> ids) {
        log.info("send a response with the departments of the assigned IDs");
        List<DepartmentDto> departments = (List<DepartmentDto>) DEPARTMENT_MAPPER.toDto(departmentService.findByAllId(ids));
        log.info("The operation was successful, we got the departments by id = {} ",ids);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    /**
     * добавляет департамент
     *
     * @param departmentDto
     * @return
     */
    @Operation(summary = "Добавлнение нового департамента")
    @PostMapping
    private ResponseEntity<Long> saveDepartment(@RequestBody DepartmentDto departmentDto) {
        log.info("Starting the save operation");
        Long idSaveDep = departmentService.save(DEPARTMENT_MAPPER.toEntity(departmentDto));
        log.info("saving the department and displaying its full name in the response");
        return new ResponseEntity<>( idSaveDep, HttpStatus.OK);
    }

    /**
     * заносит дату архивации в департамент, обозначая тем самым, архивацию
     *
     * @param id
     * @return
     */
    @Operation(summary = "Архивация департамента с занесением времени архивации")
    @PostMapping("/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        log.info("Starting the archiving operation");
        departmentService.removeToArchived(id);
        log.info("Archiving a department");
        return new ResponseEntity<>("The department is archived", HttpStatus.OK);
    }

}
