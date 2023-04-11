package com.education.service.deadlineResolution.impl;

import com.education.service.deadlineResolution.DeadlineResolutionService;
import lombok.AllArgsConstructor;
import model.dto.DeadlineResolutionDto;
import model.dto.ResolutionDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.EDO_REPOSITORY_NAME;

/**
 * Сервис-слой для DeadlineResolution
 */
@Service
@AllArgsConstructor
public class DeadlineResolutionServiceImpl implements DeadlineResolutionService {

    private RestTemplate restTemplate;
    public static final String RESOLUTION_URL_DEADLINE = "api/repository/resolution/deadline";

    /**
     * метод получает DeadlineResolutionDto и отправляет его в api/repository/resolution/deadline, с помощью restTemplate
     */
    @Override
    public void reasonForTransferDeadline(DeadlineResolutionDto deadlineResolutionDto) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL_DEADLINE);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(builder.toString(),
                HttpMethod.POST,
                new HttpEntity<>(deadlineResolutionDto, headers),
                ResolutionDto.class);
    }
}
