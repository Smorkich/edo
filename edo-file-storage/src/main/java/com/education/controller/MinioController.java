package com.education.controller;

import com.education.component.MinioComponent;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * RestController of edo-file-storage.
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/file-storage")
public class MinioController {
    @Autowired
    private MinioComponent minioComponent;

    public MinioController() {

    }


    /**
     * Request to upload file from bucket of MINIO-server.
     * Request consist of object`s name.
     */
    @ApiOperation("send request to upload file to buckets from source")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity uploadFileToMinIO(@RequestParam("file") MultipartFile file) {
        try(InputStream in = new ByteArrayInputStream(file.getBytes())) {
            String fileName = UUID.randomUUID().toString();
            String contentType = file.getContentType();
            minioComponent.postObject(fileName, in, contentType);
            return ResponseEntity.ok().body("File is uploaded. Name: " + fileName + " type: " + contentType);
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
            case "pdf":
                contentType = MediaType.APPLICATION_PDF;
                break;
            case "png":
                contentType = MediaType.IMAGE_PNG;
                break;
            case "jpeg":
                contentType = MediaType.IMAGE_JPEG;
                break;
            case "doc":
                contentType = new MediaType("application", "msword");
                break;
            case "docx":
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
