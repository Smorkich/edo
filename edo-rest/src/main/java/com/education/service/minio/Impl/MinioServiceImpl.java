package com.education.service.minio.Impl;

import com.education.service.minio.MinioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@AllArgsConstructor
public class MinioServiceImpl implements MinioService {

    private static final String URL = "http://edo-file-storage/api/filestorage/minio";
    private RestTemplate restTemplate;

    @Override
    public void checkConnection() {
        restTemplate.getForObject(URL + "/checkConnection", Void.class);
    }

    @Override
    public void uploadOneFile(String objectName) {
        restTemplate.getForObject(URL + "/upload/" + objectName, Void.class);
    }

    @Override
    public void downloadOneFile(String objectName) {
        restTemplate.getForObject(URL + "/download/" + objectName, Void.class);
    }
}
