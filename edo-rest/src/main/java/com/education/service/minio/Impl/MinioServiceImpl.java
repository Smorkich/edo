package com.education.service.minio.Impl;

import com.education.service.minio.MinioService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
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

import static model.constant.Constant.EDO_SERVICE_NAME;

@Service
@AllArgsConstructor
public class MinioServiceImpl implements MinioService {

    private static final String URL = "http://edo-file-storage/api/file-storage";
    private RestTemplate restTemplate;

    @Override
    public void checkConnection() {
        restTemplate.getForObject(URL + "/checkConnection", Void.class);
    }

    @Override
    public void uploadOneFile(MultipartFile currentFile) throws IOException {

        String extension = StringUtils.getFilenameExtension(currentFile.getOriginalFilename());

        Path tempFile = Files.createTempFile(null, String.format(".%s", extension));
        Files.write(tempFile, currentFile.getBytes());
        File fileToSend = tempFile.toFile();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(fileToSend));

        HttpEntity requestEntity = new HttpEntity<>(body, headers);

        String uri = URIBuilderUtil.buildURI(EDO_SERVICE_NAME, "/api/service/minio/upload")
                .toString();

        try {
            restTemplate.postForObject(uri, requestEntity, String.class);
        } finally {
            fileToSend.delete();
        }

    }

    @Override
    public void downloadOneFile(String objectName) {
        restTemplate.getForObject(URL + "/download/" + objectName, Void.class);
    }

    @Override
    public boolean isAvailable(MultipartFile currentFile) {

        String extension = StringUtils.getFilenameExtension(currentFile.getOriginalFilename());
        boolean isAvailable = extension.equals("doc") || extension.equals("docx")
                || extension.equals("png") || extension.equals("jpeg") || extension.equals("jpg");
        return isAvailable;

    }
}
