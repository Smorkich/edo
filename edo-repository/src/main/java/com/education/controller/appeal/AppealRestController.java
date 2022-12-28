package com.education.controller.appeal;

import static com.education.mapper.AppealMapper.APPEAL_MAPPER;

import com.education.mapper.AppealMapper;
import com.education.service.appeal.AppealService;
import com.education.service.employee.EmployeeService;
import com.education.service.nomenclature.NomenclatureService;
import lombok.AllArgsConstructor;
import model.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/api/repository/appeal")
public class AppealRestController {

    private AppealService appealService;

    private EmployeeService employeeService;

    private NomenclatureService nomenclatureService;


    private AppealMapper appealMapper;

    @PatchMapping(value = "/move/{id}")
    public ResponseEntity<AppealDto> moveToArchive(@PathVariable Long id) {
        appealService.moveToArchive(id, ZonedDateTime.now());
        return new ResponseEntity<>(APPEAL_MAPPER.toDto(appealService.findById(id)), HttpStatus.OK);
    }

    @GetMapping(value = "/findAllNotArchived", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AppealDto>> findAllNotArchived() {
        var appealDtoCollection = APPEAL_MAPPER.toDto(appealService.findAllNotArchived());
        return new ResponseEntity<>(appealDtoCollection, HttpStatus.OK);
    }

    @GetMapping(value = "/findByIdNotArchived/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> findByIdNotArchived(@PathVariable Long id) {
        return new ResponseEntity<>(APPEAL_MAPPER.toDto(appealService.findByIdNotArchived(id)), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AppealDto>> getAllAppeal() {
        var appealDtoCollection = APPEAL_MAPPER.toDto(appealService.findAll());
        return new ResponseEntity<>(appealDtoCollection, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> deleteAppeal(@PathVariable(value = "id") Long id) {
        appealService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> saveAppeal(@RequestBody AppealDto appealDto) {
        appealService.save(APPEAL_MAPPER.toEntity(appealDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> getAppealById(@PathVariable Long id) {
        return new ResponseEntity<>(APPEAL_MAPPER.toDto(appealService.findById(id)), HttpStatus.OK);
    }


}
