package com.education.controller.minio;

import com.education.service.minio.MinioService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Anna Artemyeva
 */

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/service/minio")
public class MinioController {

    private final MinioService minioService;

    @ApiOperation(value = "Uploading file to file storage")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> uploadOneFile(@RequestParam("file") MultipartFile file) {
        log.info("Start upload file named: {}", file.getOriginalFilename());
        minioService.uploadOneFile(file);
        log.info("End upload file named: {}", file.getOriginalFilename());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
