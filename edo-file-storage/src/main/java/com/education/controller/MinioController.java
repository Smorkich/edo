package com.education.controller;

import com.education.component.MinioComponent;
import com.education.exceptions.ExtensionException;
import com.education.exceptions.MinIOPutException;
import com.education.exceptions.SizeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EnumFileType;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static model.dto.EnumFileType.FACSIMILE;
import static model.dto.EnumFileType.MAIN;


/**
 * RestController of edo-file-storage.
 */
@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Контроллер для работы с MiniO", description = "Отправляет запрос к MiniO")
@RequestMapping("/api/file-storage")
public class MinioController {

    private MinioComponent minioComponent;

    /**
     * Request to upload file from bucket of MINIO-server.
     * Request consist of object`s name.
     */
    @Operation(summary = "Отправляет запрос на загрузку файла в корзину из исходного кода")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String uploadFileToMinIO(@RequestParam("file") MultipartFile file,
                                    @RequestParam("key") String key,
                                    @RequestParam("fileType") EnumFileType fileType) throws IOException {

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());


        if (FACSIMILE.equals(fileType) &&
                !((Objects.requireNonNull(extension).equals("jpg")) || (extension.equals("jpeg")) || (extension.equals("png")))) {
            throw new ExtensionException("Неверное расширение файла!");
        }

        //проверка нужна только для файлов с расширениями jpeg jpg png, тк остальные файлы это не изображение
        if ( (Objects.requireNonNull(extension).equals("jpg")) || (extension.equals("jpeg")) || (extension.equals("png"))){
           //Изменено получение изображения из файла, тк происходит ошибка
            // Image image = new ImageIcon((Image) file).getImage();
            Image image = ImageIO.read(file.getInputStream());
            if (image.getHeight(null) > 100 || image.getWidth(null) > 100) {
                throw new SizeException("Превышен допустимый размер файла");
            }
        }


        if (MAIN.equals(fileType)) {

            try (var convertedFile = minioComponent.convertFileToPDF(file, extension)) {
                String contentType = minioComponent.getFileContentType(file, extension);
                log.info("Uploading file with key: {}; And type: {}", key, contentType);
                minioComponent.postObject(
                        minioComponent.getFileName(key, extension),
                        convertedFile,
                        contentType);
                return contentType;
            } catch (IOException e) {
                log.error("bed request");
                return "Something wrong.";
            }

        } else {
            String contentType = minioComponent.getFileContentType(file, extension);
            log.info("Uploading file with key: {}; And type: {}", key, contentType);
            minioComponent.postObject(
                    minioComponent.getFileName(key, extension),
                    null,
                    contentType);
            return contentType;
        }
    }

    /**
     * Request to download file from MINIO-server.
     * Request consist of object`s name.
     */
    @Operation(summary = "Отправляет запрос на загрузку файла с сервера")
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("id") String fileName) {
        log.info("Download file :  {}", fileName);
        //проверим содержит ли наименование расширение *.pdf и при необходимости добавим его
        if (!fileName.endsWith(".pdf")){ fileName+=".pdf";};
        InputStream is = minioComponent.getObject(fileName);
        return ResponseEntity.ok()
                .body(new InputStreamResource(is));
    }


    /**
     * Request to delete old file in the MINIO-server`s bucket
     */
    @Operation(summary = "Отправляет запрос на удаление устаревших файлов в корзине MiniO")
    @DeleteMapping("/delete/{storageFileId}")
    public ResponseEntity delete(@PathVariable("storageFileId") String storageFileId) {
        log.info("Delete outdated objects in MINIO-server");
        //проверим содержит ли наименование расширение *.pdf и при необходимости добавим его
        if (!storageFileId.endsWith(".pdf")){ storageFileId+=".pdf";};
        InputStream is = minioComponent.getObject(storageFileId);
        if (is != null) {
            minioComponent.deleteObjects(storageFileId);
            log.info("Delete outdated objects in MINIO-server successful");
            return ResponseEntity.ok().body("File is deleted");
        } else {
            log.warn("Delete outdated objects in MINIO-server went wrong");
            return ResponseEntity.badRequest().body("File with name " + storageFileId + " is not found");
        }
    }

    /**
     * Request, checking the connection to MINIO-server
     */
    @Operation(summary = "Проверка подключения к серверу MiniO")
    @GetMapping("/checkConnection")
    public ResponseEntity checkConnection() {
        log.info("Checking connection");
        try {
            minioComponent.checkConnection();
        } catch (MinIOPutException e) {
            log.warn("Connection is not established");
            return ResponseEntity.internalServerError().body("Connection is not established. Reason: " + e.getMessage());
        }
        log.info("Connection checked");
        return ResponseEntity.ok().body("Connection checked");
    }
}
