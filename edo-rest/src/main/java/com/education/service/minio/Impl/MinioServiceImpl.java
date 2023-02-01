package com.education.service.minio.Impl;

import com.education.service.minio.MinioService;
import com.education.util.URIBuilderUtil;
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

import static model.constant.Constant.DOC;
import static model.constant.Constant.DOCX;
import static model.constant.Constant.EDO_SERVICE_NAME;
import static model.constant.Constant.JPEG;
import static model.constant.Constant.JPG;
import static model.constant.Constant.PDF;
import static model.constant.Constant.PNG;

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
//    private static final String URL = "http://edo-file-storage/api/file-storage";
    private static final String URL_SERVICE = "http://edo-service/api/service/minio";
    private RestTemplate restTemplate;

    @Override
    public void uploadOneFile(MultipartFile currentFile) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", currentFile.getResource());
        body.add("name", currentFile.getOriginalFilename());

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String uri = URIBuilderUtil.buildURI(EDO_SERVICE_NAME, "/api/service/minio/upload")
                .toString();

        restTemplate.postForEntity(uri,
                requestEntity,
                String.class);
    }

    @Override
    public Resource downloadOneFile(String objectName, String type) {
        return restTemplate.getForObject(URL_SERVICE + "/download/" + objectName + "?type=" + type, Resource.class);
    }

    @Override
    public void delete(String name) {
        restTemplate.delete(URL_SERVICE + "/delete/" + name, Void.class);
    }

    @Override
    public boolean isAvailable(MultipartFile currentFile) {
        String extension = StringUtils.getFilenameExtension(currentFile.getOriginalFilename());
        return DOC.equals(extension) || DOCX.equals(extension)
                || PNG.equals(extension) || JPEG.equals(extension)
                || JPG.equals(extension) || PDF.equals(extension);
    }

    @Override
    public boolean isAvailable(MultipartFile currentFile) {
        String extension = StringUtils.getFilenameExtension(currentFile.getOriginalFilename());
        return DOC.equals(extension) || DOCX.equals(extension)
                || PNG.equals(extension) || JPEG.equals(extension) || JPG.equals(extension);
    }
}
