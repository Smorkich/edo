package com.education.service.minio.impl;

import com.education.service.minio.MinioService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;

/**
 * @author Anna Artemyeva
 */
@Service
@Log4j2
@AllArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final RestTemplate restTemplate;

    @Override
    public void uploadOneFile(MultipartFile currentFile)  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", currentFile.getResource());

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        var builder = buildURI(EDO_REPOSITORY_NAME, FILEPOOL_URL)
                .setPath("/api/file-storage/upload").toString();

        restTemplate.postForEntity(builder,
                requestEntity,
                String.class);
    }
}
