package com.education.controller.facsimile;

import com.education.service.facsimile.FacsimileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FacsimileDto;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/service/facsimile")
@AllArgsConstructor
@Tag(name = "Контроллер для работы с FacsimileDto")
public class FacsimileController {

    private FacsimileService facsimileService;

    @Operation(summary = "Принимает facsimile, отправляет на edo-repo")
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public FacsimileDto save(@RequestBody FacsimileDto facsimileDto) {
        return facsimileService.save(facsimileDto);
    }

    @Operation(summary = "Производит поиск Facsimile по EmployeeId")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FacsimileDto> findFacsimileByEmployeeId(@PathVariable Long id) {
        log.info("Поиск факсимиле по id Employee");
        var facsimileDto = facsimileService.findFacsimileByEmployeeId(id);
        log.info("Операция прошла успешно, был получен факсимиле по EmployeeId = {}", id);
        return new ResponseEntity<>(facsimileDto, HttpStatus.OK);
    }

    @Operation(summary = "Производит загрузку файла Facsimile из сервиса MinIo по EmployeeId")
    @GetMapping(value = "/{id}/download", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> getFacsimileFileByEmployeeId(@PathVariable Long id) {
        log.info("Поиск файла факсимиле по id Employee");
        var facsimileDto = facsimileService.findFacsimileByEmployeeId(id);
        var facsimileFile = facsimileService.getFacsimile(facsimileDto);
        log.info("Операция прошла успешно, был загружен файл факсимиле по EmployeeId = {}", id);
        return new ResponseEntity<>(facsimileFile, HttpStatus.OK);
    }
}
