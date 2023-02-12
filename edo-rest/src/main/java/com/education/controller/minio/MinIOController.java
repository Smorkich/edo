package com.education.controller.minio;

import com.education.service.minio.MinioService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;

/**
 * RestController of edo-rest for redirect
 * request to rest-controller of edo-file-storage.
 */
@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/rest/minio")
public class MinIOController {

    private MinioService service;

    /**
     * Redirect request to upload file from bucket of MINIO-server.
     * Request consist of object`s name.
     */
    @ApiOperation("send request to upload file to buckets from source")
    @PostMapping("/upload")
    public ResponseEntity<HttpStatus> uploadOneFile(@RequestParam("file") MultipartFile file) throws IOException {
        service.uploadOneFile(file);
        log.info("Upload file named: {}", file.getOriginalFilename());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Redirect request to downliad file from MINIO-server.
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
     * Redirect request, checking the connection to MINIO-server
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
