package com.education.service.facsimile.impl;

import com.education.service.facsimile.FacsimileService;
import com.education.service.minio.MinioService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.constant.Constant;
import model.dto.EmployeeDto;
import model.dto.FacsimileDto;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static model.constant.Constant.EDO_REPOSITORY_NAME;

@Service
@Log4j2
@AllArgsConstructor
public class FacsimileServiceImpl implements FacsimileService {

    private final RestTemplate restTemplate;
    private final MinioService minioService;

    @Override
    public FacsimileDto save(FacsimileDto facsimileDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "api/repository/facsimile/save").toString();
        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(facsimileDto, headers), FacsimileDto.class).getBody();

    }

    @Override
    public FacsimileDto findFacsimileByEmployeeId(Long id) {
        log.info("Отправляем запрос на получение Facsimile по EmployeeId в edo-repository");
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "api/repository/facsimile/" + id).toString();
        return restTemplate.getForObject(uri, FacsimileDto.class);
    }

    @Override
    public Resource getFacsimile(FacsimileDto facsimile) {

        String storageKey = String.valueOf(facsimile.getFilePoolId().getStorageFileId());
        if (storageKey != null) {
            log.info("Файл факсимле получен!");
        } else {
            log.warn("Файл факсимиле не был найден!");
        }

        try {
            // Скачать файл факсимиле из MinIO
            Resource facsimileData = minioService.downloadOneFile(storageKey);
            return facsimileData;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get facsimile from MiniO Container " + e);
        }
    }

}
