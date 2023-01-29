package com.education.service.minio.Impl;

import com.education.service.minio.MinioService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static model.constant.Constant.DOC;
import static model.constant.Constant.DOCX;
import static model.constant.Constant.EDO_SERVICE_NAME;
import static model.constant.Constant.JPEG;
import static model.constant.Constant.JPG;
import static model.constant.Constant.PNG;

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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", currentFile.getResource());

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String uri = URIBuilderUtil.buildURI(EDO_SERVICE_NAME, "/api/service/minio/upload")
                .toString();

        restTemplate.postForEntity(uri,
                requestEntity,
                String.class);
    }

    @Override
    public void downloadOneFile(String objectName) {
        restTemplate.getForObject(URL + "/download/" + objectName, Void.class);
    }

    @Override
    public boolean isAvailable(MultipartFile currentFile) {
        String extension = StringUtils.getFilenameExtension(currentFile.getOriginalFilename());
        return DOC.equals(extension) || DOCX.equals(extension)
                || PNG.equals(extension) || JPEG.equals(extension) || JPG.equals(extension);
    }
}
