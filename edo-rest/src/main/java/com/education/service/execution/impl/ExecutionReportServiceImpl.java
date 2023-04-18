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

/**
 * @author Nikita Sheikin
 * Service в "edo-rest", служит для связи контроллера и RestTemplate
 */
@Log4j2
@Service
@AllArgsConstructor
public class ExecutionReportServiceImpl implements ExecutionReportService {
    private final String SERVICE_URL = "api/service/report";
    private final RestTemplate restTemplate;

    /**
     * Отправляет ExecutionReportDto в edo-service на сохранение
     *
     * @param reportDto
     * @return ExecutionReportDto
     */
    @Override
    public ExecutionReportDto submitReport(ExecutionReportDto reportDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.info("Создаем URI для edo-service ");
        String uri = URIBuilderUtil.buildURI(Constant.EDO_SERVICE_NAME, SERVICE_URL).toString();
        log.info("Отправляем reportDto в edo-service");
        return restTemplate.exchange(uri,
                HttpMethod.POST,
                new HttpEntity<>(reportDto, headers),
                ExecutionReportDto.class
        ).getBody();
    }
}