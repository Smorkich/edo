package com.education.controller.execution;

import com.education.service.execution.ExecutionReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.ExecutionReportDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nikita Sheikin
 * Контроллер для работы с ExecutionReportDto *
 * Служит для связи с ExecutionReportService
 */
@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/rest/report")
@Tag(name = "Отчеты", description = "Методы для работы с отчетами по резолюциям")
public class ExecutionReportController {
    private final ExecutionReportService reportService;

    @Operation(summary = "Добавляет отчет о статусе выполнения резолюции")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExecutionReportDto> submitReport(@RequestBody ExecutionReportDto reportDto) {
        log.info("Отправить пост запрос в edo-service");
        var report = reportService.submitReport(reportDto);
        log.info("{} отправлен в edo-service", reportDto);
        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }
}
