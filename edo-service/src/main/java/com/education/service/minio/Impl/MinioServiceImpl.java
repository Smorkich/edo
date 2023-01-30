package com.education.service.minio.Impl;

import com.education.service.filePool.FilePoolService;
import com.education.service.minio.MinioService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FilePoolDto;
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
import java.util.UUID;

import static model.constant.Constant.EDO_FILE_STORAGE_NAME;
import static model.constant.Constant.PDF;

@Service
@Log4j2
@AllArgsConstructor
public class MinioServiceImpl implements MinioService {
    private static final String URL_FILE_STORAGE = "http://edo-file-storage/api/file-storage";// исправить в константах
    private FilePoolService filePoolService;
    private RestTemplate restTemplate;

    @Override
    public void uploadOneFile(MultipartFile currentFile, UUID UUIDKey, String fileName, String contentType) throws IOException {
//        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
//        Path tempFile = Files.createTempFile(null, String.format(".%s", extension));
//        Files.write(tempFile, file.getBytes());
//        File fileToSend = tempFile.toFile();

//        filePoolService.save(FilePoolDto.builder()
//                .storageFileId(UUIDKey) //Ключ для получения файла из хранилища
//                .name(fileName) //Имя обращения
//                .extension(contentType) //Формат файла
//                .size(currentFile.getSize()) //Размер обращения
////                    .pageCount() //Количество страниц
////                    .uploadDate() //Дата создания. Нужна реализация авторизации
////                    .archivedDate() //Дата архивации. Нужна реализация авторизации
////                    .creator() //id создателя файла. Нужна реализация авторизации
//                .build());

        String key = String.format("%s.%s", UUIDKey.toString(), PDF);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", currentFile.getResource());
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("file", new FileSystemResource(fileToSend));
        body.add("key", key);
        body.add("fileName", fileName);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

//        String uri = URIBuilderUtil.buildURI(EDO_FILE_STORAGE_NAME, "/api/file-storage/upload")
//                .toString();

        restTemplate.postForEntity(URL_FILE_STORAGE + "/upload",
                requestEntity,
                String.class);
//        HttpEntity requestEntity = new HttpEntity<>(body, headers);
//        try {
//            restTemplate.postForObject(URL_FILE_STORAGE + "/upload",
//                    requestEntity,
//                    String.class);
//        } finally {
//            fileToSend.delete();
//        }
    }

    @Override
    public Resource downloadOneFile(String objectName, String type) {
        return restTemplate.getForObject(URL_FILE_STORAGE + "/download/" + objectName + "?type=" + type, Resource.class);
    }

    @Override
    public void delete(String name) {
        restTemplate.delete(URL_FILE_STORAGE + "/delete/" + name, Void.class);
    }
}
