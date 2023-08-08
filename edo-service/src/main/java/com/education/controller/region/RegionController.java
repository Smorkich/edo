package com.education.controller.region;

import com.education.service.region.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.RegionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Collection;

/**
 * Rest-контроллер в "edo-service", служит для отправки запросов
 * от клиента(edo-service) к другому микро-сервису(edo-repository) используя RestTemplate
 * и для получения данных в ответ.
 * "@Log4j2" нужна для создания логов, для удобной отладки программы
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/service/region")
@Tag(name = "Rest- контроллер для работы с регионами")
public class RegionController {

    /**
     * Поле "regionService" нужно для вызова Service-слоя (edo-service),
     * который нужен для связи с RestTemplate (edo-service)
     */
    private final RegionService regionService;

    @Operation(summary = "Сохраняет регион в базу", description = "Регион должен существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionDto> save(@RequestBody RegionDto regionDto) {
        log.info("Send a post-request to save new Region to database");
        regionService.save(regionDto);
        log.info("Response: {} was added to database", regionDto);
        return new ResponseEntity<>(regionDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Удаляет регион по id", description = "Регион должен существовать")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        log.info("Send a delete-request to remove Region with id= {}", id);
        regionService.delete(id);
        log.info("Response: Region with id = {} was deleted from database", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Operation(summary = "Возвращает регион по id", description = "Регион должен существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionDto> findById(@PathVariable(name = "id") long id) {
        log.info("Send a get-request to get Region with id = {} from database", id);
        var regionDto = regionService.findById(id);
        log.info("Response from database: {}", regionDto);
        return new ResponseEntity<>(regionDto, HttpStatus.OK);
    }

    @Operation(summary = "Возвращает все регионы")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RegionDto>> findAll() {
        log.info("Send a get-request to get all Regions from database");
        var regionDto = regionService.findAll();
        log.info("Response from database: {}", regionDto);
        return new ResponseEntity<>(regionDto, HttpStatus.OK);
    }

    @Operation(summary = "Заполняет дату архивации")
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> moveToArchive(@PathVariable("id") Long id) {
        log.info("Adding archived date {} in region with id = {}", ZonedDateTime.now(), id);
        regionService.moveToArchive(id);
        log.info("Response: {} archiving", id);
        return new ResponseEntity<>("The Region is archived", HttpStatus.OK);
    }
}
