package com.education.controller.appeal;

import com.education.publisher.nomenclature.impl.NomenclaturePublisher;
import com.education.service.appeal.AppealService;
import com.education.service.minio.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import model.dto.FilePoolDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Rest-контроллер в "edo-rest", служит для отправки обращения (Appeal) в БД используя RestTemplate
 */
@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/edo/appeal")
@Tag(name= "Обращения", description = "Методы для работы с обращениями")
public class AppealController {
    private final AppealService appealService;

    private final MinioService minioService;
    private final NomenclaturePublisher nomenclaturePublisher;

    @Operation(summary = "Принимает обращение", description = "Позволяет отправить обращение на edo-service")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> save(@RequestBody AppealDto appealDto) {
        log.info("Отправить пост-запрос в edo-service");
        var save = appealService.save(appealDto);
        nomenclaturePublisher.produce(appealDto.getNomenclature());
        log.info(" пост-запрос отправлен в edo-service");
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @Operation(summary = "Заполняет поле archivedDate в таблице Appeal", description = "")
    @PutMapping(value = "/move/{id}")
    public ResponseEntity<AppealDto> moveToArchive(@PathVariable Long id) {
        appealService.moveToArchive(id);
        log.info("Moving appeal with id: {} to edo-service is success!", id);
        return new ResponseEntity<>(appealService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Поиск обращения по id")
    @GetMapping("/{id}")
    public ResponseEntity<AppealDto> getById(@PathVariable Long id) {
        log.info("Получаем AppealDto по id");
        var appealDto = appealService.findById(id);
        log.info("AppealDto успешно получен");
        return new ResponseEntity<>(appealService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Добавляет файл к выбранному обращению")
    @PostMapping("/upload")
    public ResponseEntity<AppealDto> uploadFile(@RequestParam(value = "id", required = true) Long id, @RequestParam(value = "file", required = true) MultipartFile file) {
        log.info("Получаем объект FilePoolDto");
        FilePoolDto filePoolDto = minioService.uploadOneFile(file);
        log.info("Файл получен - " + filePoolDto.getName());
        log.info("Прикрепление файла к обращению");
        var save = appealService.upload(id, filePoolDto);
        log.info("Файл прикреплён ");
        return new ResponseEntity<>(save, HttpStatus.OK);
    }
}
