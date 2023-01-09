package com.education.controller;

import com.education.service.Impl.MinioServiceImpl;
import com.education.service.MinioService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalTime;

/**
 * RestController of edo-file-storage.
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/filestorage/minio")
public class MinioController {


    private MinioService service;

    /**
     * Request to upload file from bucket of MINIO-server.
     * Request consist of object`s name.
     */
    @ApiOperation("send request to upload file to buckets from source")
    @GetMapping("/upload/{name}")
    public ResponseEntity<HttpStatus> uploadOneFile(@PathVariable("name") String name) throws IOException {
        log.info("Upload file named: ", name);
        service.uploadOneFile(name);
        log.info("Upload file named: ", name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Request to downliad file from MINIO-server.
     * Request consist of object`s name.
     */
    @ApiOperation("send request to download file from server`s bucjets to target folder")
    @GetMapping("/download/{name}")
    public ResponseEntity<HttpStatus> downloadOneFile(@PathVariable("name") String name) {
        log.info("Download file named: ", name);
        service.downloadOneFile(name);
        log.info("Download file named: ", name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Request to delete old file in the MINIO-server`s bucket
     */
    @ApiOperation("send request to upload file to bucjets from source")
    @GetMapping("/delete")
    public ResponseEntity<HttpStatus> delete(@PathVariable("objectNumber") String objectNumber) throws IOException {
        log.info("Delete outdated objects in MINIO-server:", LocalTime.now());
        service.deleteObjects(objectNumber);
        log.info("Delete outdated objects in MINIO-server:", LocalTime.now());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Request, checking the connection to MINIO-server
     */
    @ApiOperation("Checking connection to MinIo server.")
    @GetMapping("/checkConnection")
    public ResponseEntity<HttpStatus> checkConnection() {
        log.info("check connection:", LocalTime.now());
        service.checkConnection();
        log.info("check connection:", LocalTime.now());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
