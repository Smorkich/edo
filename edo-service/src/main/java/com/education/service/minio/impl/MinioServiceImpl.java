package com.education.service.minio.impl;

import com.education.service.minio.MinioService;
import com.education.util.URIBuilderUtil;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

import static model.constant.Constant.EDO_FILE_STORAGE_NAME;
import static model.constant.Constant.FILEPOOL_URL;

/**
 * @author Anna Artemyeva
 */
@Service
@Log4j2
@AllArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    @Override
    public void uploadOneFile(MultipartFile currentFile)  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", currentFile.getResource());

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        var instances = eurekaClient.getApplication(EDO_FILE_STORAGE_NAME).getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        String uri = builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath("/api/file-storage/upload").toString();

        restTemplate.postForEntity(uri,
                requestEntity,
                String.class);
    }

}
