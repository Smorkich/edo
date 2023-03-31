package com.education.controller.recognition;

import com.education.publisher.recognition.RecognitionPublisher;
import com.education.service.appeal.AppealService;
import com.education.service.minio.MinioService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import model.dto.FilePoolDto;
import model.dto.RecognitionDto;
import model.file.ByteArrayFileContainer;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Rest-контроллер в "edo-rest", служит для отправки файла на распознавание по id Appeal
 */
@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/rest/recognition")
public class RecognitionController {

    private final AppealService appealService;
    private final RecognitionPublisher publisher;
    private final MinioService minioService;

    @ApiOperation(value = "Принимает id обращения, достает по нему файл из file-storage, отправляет на распознавание в очередь FILE_RECOGNITION_START")
    @GetMapping("/{id}")
    public ResponseEntity<HttpStatus> recognition(@PathVariable("id") Long id) {
        log.info("Отправялем запрос на получение appealDto");
        AppealDto appealDto = appealService.findById(id);
        log.info("appealDto получен");
        var filesUuid = appealDto.getFile()
                .stream()
                .map(file -> file.getStorageFileId().toString())
                .collect(Collectors.toSet());

        var resources = filesUuid
                .stream()
                .map(minioService::downloadOneFile)
                .collect(Collectors.toSet());
        var is = resources.stream().map(resource -> {
            try {
                return resource.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toSet());

        Set<byte[]> bytes = is.stream().map(inputStream -> {
            try {
                return inputStream.readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toSet());

        publisher.recognize(RecognitionDto.builder()
                .recognitionId(UUID.randomUUID()).file(bytes)
                .build()
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
