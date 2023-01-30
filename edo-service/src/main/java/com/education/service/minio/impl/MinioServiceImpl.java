package com.education.service.minio.impl;

import com.education.service.minio.MinioService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import static model.constant.Constant.EDO_FILE_STORAGE_NAME;

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

        String uri = URIBuilderUtil.buildURI(EDO_FILE_STORAGE_NAME, "/api/file-storage/upload")
                .toString();

        restTemplate.postForEntity(uri,
                requestEntity,
                String.class);
    }

}
