package com.education.controller.resolution;

import com.education.service.email.EmailService;
import com.education.service.resolution.ResolutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.ResolutionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static model.constant.Constant.RESOLUTION_SERVICE_URL;

/**
 * REST контроллер для отправки запросов к БД
 */
@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Rest- контроллер для работы с резолюциями")
@RequestMapping(RESOLUTION_SERVICE_URL)
public class ResolutionController {

    /**
     * Служит для связи с сервисом ResolutionService
     */
    private ResolutionService resolutionService;
    private EmailService emailService;

    @Operation(summary = "Добавление резолюции")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResolutionDto> saveResolution(@RequestBody ResolutionDto resolutionDto) {
        log.info("POST request has been sent");
        var save = resolutionService.save(resolutionDto);
        log.info("{} has has been added", resolutionDto);
        log.info("Sending a message to employees");
        emailService.sendMessage(resolutionDto);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @Operation(summary = "Разорхивирует резолюцию")
    @PostMapping(value = "/unarchive/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResolutionDto> unarchiveResolution(@PathVariable Long id) {
        log.info("Получен запрос на разархивирование резолюции с id = {}", id);
        resolutionService.unarchiveResolution(id);
        var resolutionDto = resolutionService.findById(id);
        log.info("Резолюция разархивирована");
        return new ResponseEntity<>(resolutionDto, HttpStatus.OK);
    }

    @Operation(summary = "Перемещение резолюции в архив")
    @PostMapping(value = "/move/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResolutionDto> moveToArchive(@PathVariable Long id) {
        log.info("PATCH request has been sent");
        resolutionService.moveToArchive(id);
        var resolutionDto = resolutionService.findById(id);
        log.info("Resolution with id = {} has been moved to archive", id);
        return new ResponseEntity<>(resolutionDto, HttpStatus.OK);
    }

    @Operation(summary = "Поиск резолюции по id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ResolutionDto> findById(@PathVariable Long id) {
        log.info("GET request to search for resolution with id = {} has been sent", id);
        var resolutionDto = resolutionService.findById(id);
        log.info("Resolution with id = {} was found", id);
        return new ResponseEntity<>(resolutionDto, HttpStatus.OK);
    }

    @Operation(summary = "Поиск всех резолюций")
    @GetMapping(value = "/all/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Collection<ResolutionDto>> findAll(@PathVariable Long id) {
        log.info("GET request to search for all resolutions has been sent");
        var resolutionDto = resolutionService.findAllById(id);
        log.info("Resolutions was found");
        return new ResponseEntity<>(resolutionDto, HttpStatus.OK);
    }

    @Operation(summary = "Поиск не архивированной резолюции по id")
    @GetMapping(value = "/notArchived/{id}")
    public ResponseEntity<ResolutionDto> findByIdNotArchived(@PathVariable Long id) {
        log.info("GET request to search for an unarchived resolution has been sent");
        var resolutionDto = resolutionService.findByIdNotArchived(id);
        log.info("Resolution with id = {} was found", id);
        return new ResponseEntity<>(resolutionDto, HttpStatus.OK);
    }

    @Operation(summary = "Поиск всех не архивированных резолюций")
    @GetMapping(value = "/notArchived/all/{id}")
    public ResponseEntity<Collection<ResolutionDto>> findAllByIdNotArchived(@PathVariable Long id) {
        log.info("GET request to search for all unarchived resolutions has been sent");
        var resolutionDto = resolutionService.findAllByIdNotArchived(id);
        log.info("Resolutions was found");
        return new ResponseEntity<>(resolutionDto, HttpStatus.OK);
    }


}
