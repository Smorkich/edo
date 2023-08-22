package com.education.controller.deadlineResolution;

import com.education.service.deadlineResolution.DeadlineResolutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.DeadlineResolutionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.education.mapper.DeadlineResolutionMapper.DEADLINE_RESOLUTION_MAPPER;

/**
 * REST контроллер для отправки запросов к БД
 */
@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Rest-контроллер для работы с дедлайнами резолюции")
@RequestMapping("api/repository/resolution/deadline")
public class DeadlineResolutionController {

    /**
     * Служит для связи с сервисом DeadlineResolutionService
     */
    private final DeadlineResolutionService deadlineResolutionService;

    @Operation(summary = "Ищет все переносы крайнего срока резолюции по id резолюции")
    @GetMapping(value = "/{resolutionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<DeadlineResolutionDto>> findByResolutionId(@PathVariable Long resolutionId) {
        log.info("Send a get-request to get DeadlineResolution with resolution id = {} from database", resolutionId);
        var deadlineResolutionDtos = DEADLINE_RESOLUTION_MAPPER.toDto(deadlineResolutionService.findByResolutionId(resolutionId));
        log.info("Response from database: {}", deadlineResolutionDtos);
        return new ResponseEntity<>(deadlineResolutionDtos, HttpStatus.OK);
    }

    @Operation(summary = "Устанавливает крайний срок для резолюции, с обоснованием переноса")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeadlineResolutionDto> saveDeadlineResolution(@RequestBody DeadlineResolutionDto deadlineResolutionDto) {
        log.info("Send a post-request to save DeadlineResolution {} to database", deadlineResolutionDto);
        deadlineResolutionService.saveDeadlineResolution(DEADLINE_RESOLUTION_MAPPER.toEntity(deadlineResolutionDto));
        log.info("Response from database: {}", deadlineResolutionDto);
        return new ResponseEntity<>(deadlineResolutionDto, HttpStatus.OK);
    }
}