package com.education.controller.resolution;

import com.education.service.resolution.ResolutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.ResolutionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static model.constant.Constant.RESOLUTION_REST_URL;


/**
 * @author Aleksandr Kostenko
 * Контроллер в модуле edo-rest, работает с моделью ResolutionDto,
 * которая произошла из сущности Resolution выполняет несколько операций:
 * добавляет резолюцию

 */
/**
 * Служит для связи с сервисом ResolutionService
 */
@RestController
@AllArgsConstructor
@Log4j2
@Tag(name="Контроллер для связи с сервисом ResolutionService")
@RequestMapping(RESOLUTION_REST_URL)
public class ResolutionController {

    private final ResolutionService resolutionService;

    @Operation(summary = "Добавление резолюции")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResolutionDto> saveResolution(@RequestBody ResolutionDto resolutionDto) {
        log.info("POST request has been sent");
        var save = resolutionService.save(resolutionDto);
        log.info("{} has has been added", resolutionDto);
    return new ResponseEntity<>(save, HttpStatus.CREATED);
    }
}
