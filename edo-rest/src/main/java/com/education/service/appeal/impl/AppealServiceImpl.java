package com.education.service.appeal.impl;

import com.education.service.appeal.AppealService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.constant.Constant;
import model.dto.AppealDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static model.constant.Constant.EDO_SERVICE_NAME;

@Service
@AllArgsConstructor
public class AppealServiceImpl implements AppealService {

    private static final String SERVICE_URL = "/api/service/appeal";

    private final RestTemplate restTemplate;

    /**
     * Отправляет запрос в edo-service на сохранение AppealDTO
     */
    @Override
    public AppealDto save(AppealDto appealDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = URIBuilderUtil.buildURI(EDO_SERVICE_NAME, "api/service/appeal").toString();

        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(appealDto, headers), AppealDto.class).getBody();
    }

    @Override
    public void moveToArchive(Long id) {
        restTemplate.put(SERVICE_URL + "/move/" + id, AppealDto.class);
    }

    @Override
    public AppealDto findById(Long id) {
        String uri = getUri("/api/service/appeal/") + id;
        return restTemplate.getForObject(uri, AppealDto.class);
    }

    private static String getUri(String path) {
        return URIBuilderUtil.buildURI(EDO_SERVICE_NAME, path)
                .toString();
    }
}
