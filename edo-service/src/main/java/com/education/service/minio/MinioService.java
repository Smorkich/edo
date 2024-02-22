package com.education.service.minio;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface MinioService {
    /**
     * Method upload the file to server MinIo.
     * Variable "objectName" means name of object, uploadibg to the bucket of minio server.
     */

    //добавлен fileType
    ResponseEntity<String> uploadOneFile(MultipartFile objectName, UUID UUIDKey,
                                         String fileName, String contentType, String fileType) throws IOException;

    /**
     * Method download the file from server MinIo.
     * Variable "objectName" means name of object,
     * downloading from the bucket of minio server
     * to target folder.
     */
    Resource downloadOneFile(String objectName);

    /**
     * Method delete objects
     */
    void delete(String name);

    long countPages(UUID UUIDKey, String originalExtension, String ConvertedContentType);

    String getConvertedFilename(String key, String extension, String contentType);

    /**
     * Наложение факсимиле на первую страницу документа
     * @param UUIDKey - переменная типа UUID, которая представляет ключ (идентификатор) для файла
     * @param originalExtension - переменная, которая содержит оригинальное расширение файла (pdf/jpeg и тд)
     * @param convertedContentType - переменная, которая указывает на тип (MIME) преобразованного файла
     * @param facsimileImage - Стрим самого факсимиле
     */
    void overlayFacsimileOnFirstFile(UUID UUIDKey, String originalExtension, String convertedContentType, InputStream facsimileImage);
}
