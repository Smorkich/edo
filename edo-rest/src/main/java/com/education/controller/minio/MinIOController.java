package com.education.controller.minio;

import com.education.service.minio.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FilePoolDto;
import model.file.ByteArrayFileContainer;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.util.UUID;

import static model.constant.Constant.EDO_FILE_STORAGE_FILE_MAX_SIZE;
import static org.springframework.http.HttpHeaders.*;


/**
 * RestController of edo-rest for redirect
 * request to rest-controller of edo-file-storage.
 */
@RestController
@Log4j2
@AllArgsConstructor
@Tag(name = "Контроллер для работы с MiniO", description = "Отправляет запрос к MiniO")
@RequestMapping("/api/rest/minio")
public class MinIOController {

    private MinioService service;

    /**
     * Redirect request to upload file from a source to a bucket.
     * Request consists of the object`s name.
     */
    @Operation(summary = "Sends a request to upload a file from the source code to the bucket")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FilePoolDto uploadOneFile(@RequestParam(value = "file") MultipartFile file,
                                     @RequestParam(value = "fileType") String fileType) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("The file parameter is missing");
        }

        if (!file.getContentType().equalsIgnoreCase(fileType)) {
            throw new IllegalArgumentException("Mismatch between actual file type and specified file type");
        }
        log.info("Upload file named: {}", file.getOriginalFilename());
        if (file.getSize() > EDO_FILE_STORAGE_FILE_MAX_SIZE) {
            throw new IllegalArgumentException("Uploaded file size exceeds maximum allowed size of " + EDO_FILE_STORAGE_FILE_MAX_SIZE + " bytes");
        }

        FilePoolDto result;
        try {
            result = service.uploadOneFile(file, fileType);
        } catch (Exception e) {
            log.error("File upload failed", e);
            throw e;
        }
        return result;
    }

    /**
     * Redirect request to download file from MINIO-server.
     * Request consist of object`s name.
     */
    @Operation(summary = "Отправляет запрос на загрзку файла из корзины в целевую папку")
    @GetMapping(value = "/download/{name}")
    public ResponseEntity<InputStreamResource> downloadOneFile(@PathVariable("name") String name) {
        log.info("Download file named: {}", name);
        UUID uuid;
        try {
            uuid = UUID.fromString(name);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID string: {}", name);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var filePoolDto = service.getFilePool(uuid);
        if (filePoolDto == null) {
            log.error("File Pool DTO not found for UUID: {}", uuid);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var downloadedFile = service.downloadOneFile(name);
        if (downloadedFile == null) {
            log.error("File not found: {}", name);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try (var is = downloadedFile.getInputStream();
             var file = new ByteArrayFileContainer(is.readAllBytes(), filePoolDto.getName())) {
            final var octetStreamHeaders = createOctetStreamHeaders(filePoolDto.getName());
            final var fileResource = new InputStreamResource(file.getInputStream());
            return new ResponseEntity<>(fileResource, octetStreamHeaders, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Exception while reading file: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Request to delete old file in the MINIO-server`s bucket
     */
    @Operation(summary = "Отправляет запрос на удаление устаревших файлов в корзине")
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity delete(@PathVariable("fileName") String fileName) {
        log.info("delete file: {}", fileName);
        service.delete(fileName);
        log.info("delete file: {}", fileName);
        return ResponseEntity.ok().body("File is deleted");
    }

    public MultiValueMap<String, String> createOctetStreamHeaders(final String name) {
        final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.add(CONTENT_DISPOSITION,
                "attachment; filename*=utf-8''" + UriUtils.encodeScheme(name, "UTF-8")
        );
        headers.add(SET_COOKIE, "fileDownload=true; path=/");
        return headers;
    }

}
