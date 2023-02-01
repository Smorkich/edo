package com.education.controller;

import com.education.component.MinioComponent;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import model.dto.FilePoolDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static model.constant.Constant.DOC;
import static model.constant.Constant.DOCX;
import static model.constant.Constant.JPEG;
import static model.constant.Constant.PDF;
import static model.constant.Constant.PNG;

/**
 * RestController of edo-file-storage.
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/file-storage")
public class MinioController {

    private MinioComponent minioComponent;

    /**
     * Request to upload file from bucket of MINIO-server.
     * Request consist of object`s name.
     */
    @ApiOperation("send request to upload file to buckets from source")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> uploadFileToMinIO(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("key") String key,
                                                    @RequestParam("fileName") String fileName) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        try(var inDoc = minioComponent.convertFileToPDF(file, extension)) {
            String contentType = file.getContentType();
            minioComponent.postObject(key, in, contentType);
            log.info("Upload file named: {};  Type: {}; Key: {}.", fileName, contentType, key);
            return ResponseEntity.ok().body("File is uploaded. \nName: " + fileName + "; \ntype: " + contentType + "; \nkey: " + key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Something wrong.");
    }

    /**
     * Request to download file from MINIO-server.
     * Request consist of object`s name.
     */

    @ApiOperation("send request to download file from server`s")
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("id") String fileName,
                                                            @RequestParam("type") String type) {
        log.info("Download file :  {}", fileName);
        InputStream is = minioComponent.getObject(fileName);
        MediaType contentType = null;
        switch (type) {
            case PDF:
                contentType = MediaType.APPLICATION_PDF;
                break;
            case PNG:
                contentType = MediaType.IMAGE_PNG;
                break;
            case JPEG:
                contentType = MediaType.IMAGE_JPEG;
                break;
            case DOC:
                contentType = new MediaType("application", "msword");
                break;
            case DOCX:
                contentType = new MediaType("application", "vnd.openxmlformats-officedocument.wordprocessingml.document");
                break;
        }
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(is));
    }


    /**
     * Request to delete old file in the MINIO-server`s bucket
     */
    @ApiOperation("send request to upload file to bucjets from source")
    @DeleteMapping("/delete/{storageFileId}")
    public ResponseEntity delete(@PathVariable("storageFileId") String storageFileId) {
        log.info("Delete outdated objects in MINIO-server");
        minioComponent.deleteObjects(storageFileId);
        log.info("Delete outdated objects in MINIO-server successful");
        return ResponseEntity.ok().body("File is deleted");
    }

    /**
     * Request, checking the connection to MINIO-server
     */
    @ApiOperation("Checking connection to MinIo server.")
    @GetMapping("/checkConnection")
    public ResponseEntity checkConnection() {
        log.info("Checking connection");
        minioComponent.checkConnection();
        log.info("Connection checked");
        return ResponseEntity.ok().body("Connection checked");
    }
}
