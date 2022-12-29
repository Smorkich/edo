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

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/filestorage/minio")
public class MinioController {

    private MinioService service;

    @ApiOperation("send request to upload file to bucjets from source")
    @GetMapping("/upload/{name}")
    public ResponseEntity<HttpStatus> uploadOneFile(@PathVariable("name") String name) throws IOException {
        service.uploadOneFile(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("send request to download file from server`s bucjets to target folder")
    @GetMapping("/download/{name}")
    public ResponseEntity<HttpStatus> downloadOneFile(@PathVariable("name") String name) {
        service.downloadOneFile(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("Checking connection to MinIo server.")
    @GetMapping("/checkConnection")
    public ResponseEntity<HttpStatus> checkConnection() {
        System.out.println(service);
        service.checkConnection();
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
