package com.education.service.minio.Impl;

import com.education.service.minio.MinioService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@AllArgsConstructor
public class MinioServiceImpl implements MinioService {

    private static final String URL = "http://edo-file-storage/api/file-storage";
    private RestTemplate restTemplate;

    @Override
    public void uploadOneFile(MultipartFile file) throws IOException {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path tempFile = Files.createTempFile(null, String.format(".%s", extension));
        Files.write(tempFile, file.getBytes());
        File fileToSend = tempFile.toFile();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(fileToSend));
        HttpEntity requestEntity = new HttpEntity<>(body, headers);
        try {
            restTemplate.postForObject(URL + "/upload",
                    requestEntity,
                    String.class);
        } finally {
            fileToSend.delete();
        }
    }

    @Override
    public Resource downloadOneFile(String objectName, String type) {
        return restTemplate.getForObject(URL + "/download/" + objectName + "?type=" + type, Resource.class);
    }

    @Override
    public void delete(String name) {
        restTemplate.delete(URL + "/delete/" + name, Void.class);
    }
}
