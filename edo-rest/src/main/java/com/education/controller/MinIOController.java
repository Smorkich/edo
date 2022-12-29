package com.education.controller;


import com.education.service.minio.MinioService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/rest/minio")
public class MinIOController {


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
