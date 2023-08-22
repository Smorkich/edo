package com.education.service.facsimile.impl;

import com.education.feign.FacsimileFeignClient;
import com.education.service.facsimile.FacsimileService;
import com.education.service.minio.MinioService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FacsimileDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import static model.constant.Constant.EDO_REPOSITORY_NAME;

@Service
@Log4j2
@AllArgsConstructor
public class FacsimileServiceImpl implements FacsimileService {

    private final FacsimileFeignClient facsimileFeignClient;
    private final MinioService minioService;

    @Override
    public FacsimileDto save(FacsimileDto facsimileDto) {
        return facsimileFeignClient.save(facsimileDto);
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
