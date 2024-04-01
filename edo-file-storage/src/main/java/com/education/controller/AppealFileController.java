package com.education.controller;

import com.education.service.AppealXLSXFileGeneration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static model.constant.Constant.FILE_STORAGE_APPEAL_FILE_URL;


/**
 * RestController of edo-file-storage for generate appeal file
 */
@RestController
@Log4j2
@AllArgsConstructor
@Tag(name = "Контроллер для работы с файлами Appeal", description = "Генерирует файл информации по резолюциям обращения")
@RequestMapping(FILE_STORAGE_APPEAL_FILE_URL)
public class AppealFileController {

    private AppealXLSXFileGeneration appealService;

    /**
     * Generates an appeal file in xlsx format
     */
    @Operation(summary = "Генерация файла обращения в xlsx формате")
    @GetMapping(value = "/xlsx/{appealId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("appealId") Long appealId) {
        log.info("Received a request to generate a XLSX file");
        return Objects.requireNonNullElseGet(appealService.generateXLSXFile(appealId),
                () -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
