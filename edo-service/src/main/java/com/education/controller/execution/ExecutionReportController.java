package com.education.controller.execution;

import com.education.service.execution.ExecutionReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.ExecutionReportDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nikita Sheikin
 * REST контроллер для отправки ExecutionReportDto в ExecutionReportService
 * и последующей отправки в edo-repository
 */
@Log4j2
@RestController
@RequestMapping("/api/service/report")
@AllArgsConstructor
@Tag(name = "Rest- контроллер для отправки отчета в edo-repository")
public class ExecutionReportController {
    private final ExecutionReportService reportService;

    @Operation(summary = "Принимает отчет и отправялет его в edo-repository")
    @PostMapping
    public ResponseEntity<ExecutionReportDto> submitReport(@RequestBody ExecutionReportDto reportDto) {
        log.info("Отправляем reportDto в service");
        var report = reportService.submitReport(reportDto);
        log.info("{} отправлен в service", reportDto);
        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }
}
