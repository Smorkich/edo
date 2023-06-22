package com.education.controller.appeal;

import com.education.publisher.nomenclature.impl.NomenclaturePublisher;
import com.education.service.appeal.AppealService;
import com.education.service.minio.MinioService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import model.dto.FilePoolDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/**
 * Rest-контроллер в "edo-rest", служит для отправки обращения (Appeal) в БД используя RestTemplate
 */
@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/edo/appeal")
public class AppealController {
    private final AppealService appealService;

    private final MinioService minioService;
    private final NomenclaturePublisher nomenclaturePublisher;

    @ApiOperation(value = "Принимает обращение, отправляет на edo-service", notes = "Обращение должно существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppealDto> save(@RequestBody AppealDto appealDto) {
        log.info("Отправить пост-запрос в edo-service");
        var save = appealService.save(appealDto);
        nomenclaturePublisher.produce(appealDto.getNomenclature());
        log.info(" пост-запрос отправлен в edo-service");
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @ApiOperation(value = "В строке таблицы Appeal заполняет поле archivedDate", notes = "Строка в Appeal должна существовать")
    @PutMapping(value = "/move/{id}")
    public ResponseEntity<AppealDto> moveToArchive(@PathVariable Long id) {
        appealService.moveToArchive(id);
        log.info("Moving appeal with id: {} to edo-service is success!", id);
        return new ResponseEntity<>(appealService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Находит обращение по id")
    @GetMapping("/{id}")
    public ResponseEntity<AppealDto> getById(@PathVariable Long id) {
        log.info("Получаем AppealDto по id");
        var appealDto = appealService.findById(id);
        log.info("AppealDto успешно получен");
        return new ResponseEntity<>(appealDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Добавляет файл к выбранному обращению")
    @PostMapping("/upload")
    public ResponseEntity<AppealDto> uploadFile(@RequestParam(value = "id") Long id, @RequestParam(value = "file") MultipartFile file) {
        log.info("Получаем объект FilePoolDto");
        FilePoolDto filePoolDto = minioService.uploadOneFile(file);
        log.info("Файл получен - " + filePoolDto.getName());
        log.info("Прикрепление файла к обращению");
        var save = appealService.upload(id, filePoolDto);
        log.info("Файл прикреплён ");
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    /**
     * Принимает запрос на регистрацию Appeal по id, который передаётся в параметре запроса и
     * вызывает метод register() из AppealService микросервиса edo-rest
     *
     * @apiNote HTTP Method - POST
     * @param id идентификатор регистрируемого Appeal
     * @return ResponseEntity<AppealDto> - ResponseEntity DTO сущности Appeal (обращение)
     */
    @ApiOperation(value = "Регистрирует обращение, отправляет на edo-service", notes = "Обращение должно существовать")
    @PostMapping("/register")
    public ResponseEntity<AppealDto> registerAppeal(@RequestParam(value = "id") Long id) {
        log.info("Registration request received on edo-rest of appeal №" + id);
        var register = appealService.register(id);
        log.info("Appeal with id " + id + " has been registered on edo-rest");
        return new ResponseEntity<>(register, HttpStatus.OK);
    }

}
