package com.education.controller.minio;

import com.education.service.minio.MinioService;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static model.constant.Constant.DOC;
import static model.constant.Constant.DOCX;
import static model.constant.Constant.JPEG;
import static model.constant.Constant.PDF;
import static model.constant.Constant.PNG;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/service/minio")
public class MinIOController {
    private MinioService service;

    /**
     * Redirect request to upload file from bucket of MINIO-server.
     * Request consist of object`s name.
     */
    @ApiOperation("send request to upload file to buckets from source")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> uploadOneFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String fileName) throws IOException {
        log.info("Upload file named: {}", fileName);
//        String key = UUID.randomUUID().toString();
        UUID UUIDKey = UUID.randomUUID();
//        String key = String.format("%s.%s", UUID.randomUUID().toString(), PDF);
        String type = file.getContentType();
        service.uploadOneFile(file, UUIDKey, fileName, type);
        log.info("Upload file named: {};  Type: {}; Key: {}.", fileName, type, UUIDKey);
        return ResponseEntity.ok().body("File is uploaded. \nName: " + fileName + " \ntype: " + type);
    }

    /**
     * Redirect request to download file from MINIO-server.
     * Request consist of object`s name.
     */
    @ApiOperation("send request to download file from server`s bucjets to target folder")
    @GetMapping("/download/{name}")
    public ResponseEntity<InputStreamResource> downloadOneFile(@PathVariable("name") String name,
                                                               @RequestParam("type") String type) throws IOException {
        log.info("Download file named: {}", name);
        Resource resource = service.downloadOneFile(name, type);
        InputStream is = resource.getInputStream();
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
        log.info("Download file named: {}", name);
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(is));
    }


    /**
     * Request to delete old file in the MINIO-server`s bucket
     */
    @ApiOperation("send request to upload file to bucjets from source")
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity delete(@PathVariable("fileName") String fileName) {
        log.info("delete file: {}", fileName);
        service.delete(fileName);
        log.info("delete file: {}", fileName);
        return ResponseEntity.ok().body("File is deleted");
    }
}
