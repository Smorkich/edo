package com.education.controller;

import com.education.service.MinioService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * RestController of edo-file-storage.
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/file-storage")
public class MinioController {


    private MinioService service;

    /**
     * Request to upload file from bucket of MINIO-server.
     * Request consist of object`s name.
     */
    @ApiOperation("send request to upload file to buckets from source")
    @GetMapping("/upload/{storageFileId}")
    public ResponseEntity<HttpStatus> uploadOneFile(@PathVariable("storageFileId") String name) throws IOException {
        log.info("Upload file named: {}", name);
        service.uploadOneFile(name);
        log.info("Upload file named: {}", name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Request to download file from MINIO-server.
     * Request consist of object`s name.
     */
    @ApiOperation("send request to download file from server`s bucjets to target folder")
    @GetMapping("/download/{storageFileId}")
    public ResponseEntity<HttpStatus> downloadOneFile(@PathVariable("storageFileId") String storageFileId) {
        log.info("Download file :  {}", storageFileId);
        service.downloadOneFile(storageFileId);
        log.info("Downloaded file :  {}", storageFileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Request to delete old file in the MINIO-server`s bucket
     */
    @ApiOperation("send request to upload file to bucjets from source")
    @GetMapping("/delete/{storageFileId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("storageFileId") String storageFileId) {
        log.info("Delete outdated objects in MINIO-server");
        service.deleteObjects(storageFileId);
        log.info("Delete outdated objects in MINIO-server successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Request, checking the connection to MINIO-server
     */
    @ApiOperation("Checking connection to MinIo server.")
    @GetMapping("/checkConnection")
    public ResponseEntity<HttpStatus> checkConnection() {
        log.info("Checking connection");
        service.checkConnection();
        log.info("Connection checked");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
