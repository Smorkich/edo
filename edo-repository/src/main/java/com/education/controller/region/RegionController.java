package com.education.controller.region;


import com.education.entity.Region;
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

import static com.education.mapper.RegionMapper.REGION_MAPPER;

/**
 * Rest-контроллер в "edo-repository", служит для отправки запросов
 * от клиента(которым может быть другой микросервис) к БД.
 * "@Log4j2" нужна для создания логов, для удобной отладки программы
 */
@RestController
@Log4j2
@AllArgsConstructor
@Tag(name = "Rest- контроллер для работы с регионами")
@RequestMapping("/api/repository/region")
public class RegionController {

    /**
     * Поле "regionService" нужно для вызова Service-слоя (edo-repository),
     * который нужен для связи с репозиторием (edo-repository)
     */
    private final RegionService regionService;

    @Operation(summary = "Сохраняет регион в базу", description = "Регион должен существовать")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Region> save(@RequestBody RegionDto regionDto) {
        log.info("Send a post-request to save new Region to database");
        regionService.save(REGION_MAPPER.toEntity(regionDto));
        log.info("Response: {} was added to database", regionDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Удаляет регион по id", description = "Регион должен существовать")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        log.info("Send a delete-request to remove Region with id= {}", id);
        regionService.delete(regionService.findById(id));
        log.info("Response: Region with id = {} was deleted from database", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Operation(summary = "Возвращает регион по id", description = "Регион должен существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegionDto> findById(@PathVariable("id") Long id) {
        log.info("Send a get-request to get Region with id = {} from database", id);
        var regionDto = REGION_MAPPER.toDto(regionService.findById(id));
        log.info("Response from database: {}", regionDto);
        return new ResponseEntity<>(regionDto, HttpStatus.OK);
    }

    @Operation(summary = "Возвращает все регионы")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RegionDto>> findAll() {
        log.info("Send a get-request to get all Regions from database");
        var regionDtoCollection = REGION_MAPPER.toDto(regionService.findAll());
        log.info("Response from database: {}", regionDtoCollection);
        return new ResponseEntity<>(regionDtoCollection, HttpStatus.OK);
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
