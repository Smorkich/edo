package com.education.service.execution.impl;

import com.education.service.execution.ExecutionReportService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.constant.Constant;
import model.dto.ExecutionReportDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;

/**
 * @author Nikita Sheikin
 * Сервис слой для ExecutionReport
 */

@AllArgsConstructor
@Service
@Log4j2
public class ExecutionReportServiceImpl implements ExecutionReportService {
    private final RestTemplate restTemplate;

    /**
     * Метод отправляющий ExecutionReportDto в edo-repository на сохранение
     *
     * @param reportDto
     * @return ExecutionReportDto
     */
    @Override
    public ExecutionReportDto submitReport(ExecutionReportDto reportDto) {
        reportDto.setCreationDate(ZonedDateTime.now());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = URIBuilderUtil.buildURI(Constant.EDO_REPOSITORY_NAME, Constant.EXECUTION_REPORT_URL).toString();

        log.info("Отправляем reportDto в edo-repository");
        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(reportDto, headers), ExecutionReportDto.class).getBody();
    }
}