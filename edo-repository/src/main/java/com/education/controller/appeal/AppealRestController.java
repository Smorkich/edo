package com.education.controller.appeal;


import com.education.repository.resolution.ResolutionRepository;
import com.education.service.appeal.AppealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import model.dto.AppealFileDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Collection;

import static com.education.mapper.AppealMapper.APPEAL_MAPPER;

@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Обращения", description = "Методы для работы с обращениями")
@RequestMapping("/api/repository/appeal")
public class AppealRestController {

    private AppealService appealService;
    private ResolutionRepository resolutionRepository;

    @Operation(summary = "В строке таблицы Appeal заполняет поле archivedDate,изменяет поле status",
            description = "Строка в Appeal должна существовать")
    @PutMapping(value = "/move/{id}")
    public ResponseEntity<Void> moveToArchive(@PathVariable Long id) {
        log.info("Adding archived date {} in Appeal with id: {}", ZonedDateTime.now(), id);
        appealService.moveToArchive(id);
        log.info("Moving appeal with id: {} to archive is success!", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Находит все строки таблицы Appeal с полем acrhivedDate = null",
            description = "Строка в Appeal должна существовать")
    @GetMapping(value = "/findAllNotArchived", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AppealDto>> findAllNotArchived() {
        log.info("Getting from database all appeals with field archivedDate = null");
        var appealDtoCollection = APPEAL_MAPPER.toDto(appealService.findAllNotArchived());
        log.info("Appeals: {}", appealDtoCollection);
        return new ResponseEntity<>(appealDtoCollection, HttpStatus.OK);
    }

    @Operation(summary = "Находит строку таблицы Appeal c полем acrhivedDate = null, по заданному id",
            description = "Строка в Appeal должна существовать")
    @GetMapping(value = "/findByIdNotArchived/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> findByIdNotArchived(@PathVariable Long id) {
        log.info("Getting from database appeal with field archivedDate = null, with id: {}", id);
        var appeal = appealService.findByIdNotArchived(id);
        log.info("Appeal: {}", appeal);
        return new ResponseEntity<>(APPEAL_MAPPER.toDto(appeal), HttpStatus.OK);
    }

    @Operation(summary = "Находит все строки таблицы Appeal", description = "Строка в Appeal должна существовать")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AppealDto>> getAllAppeal() {
        log.info("Getting from database all appeals");
        var appealDtoCollection = APPEAL_MAPPER.toDto(appealService.findAll());
        log.info("Appeals: {}", appealDtoCollection);
        return new ResponseEntity<>(appealDtoCollection, HttpStatus.OK);
    }

    @Operation(summary = "Удаляет строку таблицы Appeal по id", description = "Строка в Appeal должна существовать")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> deleteAppeal(@PathVariable(value = "id") Long id) {
        log.info("Deleting from database appeal with id: {}", id);
        appealService.delete(id);
        log.info("Deleting from database appeal with id: {}, success!", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Operation(summary = "Добавляет новую строку таблицы Appeal", description = "Строка в Appeal должна существовать")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> saveAppeal(@RequestBody AppealDto appealDto) {
        log.info("Creating appeal");
        var appeal = appealService.save(APPEAL_MAPPER.toEntity(appealDto));
        log.info("Creating appeal {}, success!", appeal);
        return new ResponseEntity<>(APPEAL_MAPPER.toDto(appealService.findById(appeal.getId())), HttpStatus.CREATED);
    }

    @Operation(summary = "Находит строку таблицы Appeal по id", description = "Строка в Appeal должна существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> getAppealById(@PathVariable Long id) {
        log.info("Getting from database appeal with id: {}", id);
        var appeal = appealService.findById(id);
        log.info("Appeal: {}", appeal);
        return new ResponseEntity<>(APPEAL_MAPPER.toDto(appeal), HttpStatus.OK);
    }

    @Operation(summary = "Находит строку таблицы Appeal по Questions id", description = "Строка в Appeal должна существовать")
    @GetMapping(value = "/findAppealByQuestionsId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> findAppealByQuestionsId(@PathVariable Long id) {
        log.info("Getting from database appeal with questions id: {}", id);
        var appeal = appealService.findAppealByQuestionsId(id);
        log.info("Appeal: {}", appeal);
        return new ResponseEntity<>(APPEAL_MAPPER.toDto(appeal), HttpStatus.OK);
    }

    /**
     * Принимает запрос на регистрацию Appeal по id, который передаётся в параметре запроса и
     * вызывает метод register() из AppealService микросервиса edo-repository
     *
     * @param id идентификатор регистрируемого Appeal
     * @return ResponseEntity<AppealDto> - ResponseEntity DTO сущности Appeal (обращение)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "В строке таблицы Appeal изменяет поле appeals_status на Registered", description = "Строка в Appeal должна существовать")
    @PostMapping("/register")
    public ResponseEntity<AppealDto> registerAppeal(@RequestParam(value = "id") Long id) {
        log.info("Updating field 'appeals_status' on 'Registered' from database appeal with appeal id: {}", id);
        var appeal = appealService.register(id);
        log.info("Updating field with id {} 'appeals_status' on 'Registered', success!", appeal);
        return new ResponseEntity<>(APPEAL_MAPPER.toDto(appeal), HttpStatus.OK);
    }

    @Operation(summary = "Получение всей информации для генерации файла обращения")
    @GetMapping(value = "/download/xlsx/{appealId}")
    public Collection<AppealFileDto> findAllByAppealId(@PathVariable Long appealId) {
        log.info("Request to get all information about appeal resolutions");
        var appealInfo = appealService.findAllForAppealFileById(appealId);
        log.info("Data has been received");
        return appealInfo;
    }

}
