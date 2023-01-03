package com.education.controller.appeal;

import com.education.entity.Appeal;
import com.education.service.appeal.AppealService;
import lombok.AllArgsConstructor;
import model.dto.AppealDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Collection;

import static com.education.mapper.AppealMapper.APPEAL_MAPPER;

@RestController
@AllArgsConstructor
@RequestMapping("/api/repository/appeal")
public class AppealRestController {

    private AppealService appealService;

    @PatchMapping(value = "/move/{id}")
    public ResponseEntity<AppealDto> moveToArchive(@PathVariable Long id) {
        appealService.moveToArchive(id, ZonedDateTime.now());
        return new ResponseEntity<>(HttpStatus.OK);
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
        Appeal save = appealService.save(APPEAL_MAPPER.toEntity(appealDto));
        return new ResponseEntity<>(APPEAL_MAPPER.toDto(save), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> getAppealById(@PathVariable Long id) {
        return new ResponseEntity<>(APPEAL_MAPPER.toDto(appealService.findById(id)), HttpStatus.OK);
    }
}
