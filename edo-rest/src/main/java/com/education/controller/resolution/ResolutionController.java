package com.education.controller.resolution;

import io.swagger.annotations.ApiOperation;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import model.dto.QuestionDto;
import model.dto.ResolutionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author Aleksandr Kostenko
 * Контроллер в модуле edo-rest, работает с моделью ResolutionDto,
 * которая произошла из сущности Resolution выполняет несколько операций:
 * добавляет департамент
 * заносит дату архиваци в департамент, обозначая тем самым, архивацию
 * достает департамент по id
 * достает департаменты по нескольким id
 * достает департамент по id и если у него нет даты архивации
 * достает департаменты по id и если у них нет даты архивации
 */
/**
 * Служит для связи с сервисом ResolutionService
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("api/rest/resolution")
public class ResolutionController {

    @ApiOperation(value = "Добавление резолюции")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResolutionDto> saveResolution(@RequestBody ResolutionDto resolutionDto) {
        log.info("POST request has been sent");
        resolutionService.save(resolutionDto);
        log.info("{} has has been added", resolutionDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
