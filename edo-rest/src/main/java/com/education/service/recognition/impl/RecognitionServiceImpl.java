package com.education.service.recognition.impl;

import com.education.publisher.recognition.RecognitionPublisher;
import com.education.service.appeal.AppealService;
import com.education.service.minio.MinioService;
import com.education.service.recognition.RecognitionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import model.dto.RecognitionDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
@Log4j2
public class RecognitionServiceImpl implements RecognitionService {
    private final RecognitionPublisher publisher;
    private final AppealService appealService;
    private final MinioService minioService;
    @Override
    public void recognize(Long id) {
        log.info("Отправляем запрос на получение appealDto");
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
    }
}
