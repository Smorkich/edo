package com.education.controller.minio;

import com.education.service.emloyee.EmployeeService;
import com.education.service.filePool.FilePoolService;
import com.education.service.minio.MinioService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import model.dto.FilePoolDto;
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
import java.time.ZonedDateTime;
import java.util.UUID;


/**
 * @author Anna Artemyeva
 */

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/service/minio")
public class MinioController {

    private final MinioService minioService;
    private final FilePoolService filePoolService;
    private final EmployeeService employeeService;

    private EmployeeDto getEmp(Long id) {
        return employeeService.findById(id);
    }

    @ApiOperation(value = "Uploading file to file storage")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = {MediaType.TEXT_PLAIN_VALUE, "application/json"})
    public String uploadOneFile(@RequestParam("file") MultipartFile file,
                                @RequestParam("name") String fileName) throws IOException {
        log.info("Upload file named: {}", fileName);
        UUID UUIDKey = UUID.randomUUID();
        String type = String.valueOf(minioService.uploadOneFile(file, UUIDKey, fileName, file.getContentType()))
                .split(",")[1];
        FilePoolDto filePoolDto = FilePoolDto.builder()
                .storageFileId(UUIDKey)                 //Ключ для получения файла из хранилища
                .name(fileName)                         //Имя обращения
                .extension(type)                        //Формат файла
                .size(file.getSize())                   //Размер обращения
                .pageCount(file.getSize())              //Количество страниц
                .uploadDate(ZonedDateTime.now())        //Дата создания.
                .creator(getEmp(4L))                //id создателя файла. Нужна реализация авторизации
                .build();
        log.info("Saving file info.");
        filePoolService.save(filePoolDto);
        log.info("Upload file named: {};  Type: {}; Key: {}.", fileName, type, UUIDKey);
        return filePoolService.findByUuid(UUIDKey).toString();
    }

    /**
     * Redirect request to download file from MINIO-server.
     * Request consist of object`s name.
     */
    @ApiOperation("send request to download file from server`s buckets to target folder")
    @GetMapping("/download/{name}")
    public ResponseEntity<InputStreamResource> downloadOneFile(@PathVariable("name") String name) throws IOException {
        log.info("Download file named: {}", name);
        Resource resource = minioService.downloadOneFile(name);
        InputStream is = resource.getInputStream();
        log.info("Download file named: {}", name);

        return ResponseEntity.ok()
                .body(new InputStreamResource(is));
    }

    @ApiOperation("get filePool by uuid")
    @GetMapping("/info/{uuid}")
    public ResponseEntity<FilePoolDto> getInfo(@PathVariable UUID uuid){
        System.out.println(uuid);
        log.info("getting a filePool by uuid");
        FilePoolDto getFilePoolByUuid = filePoolService.findByUuid(uuid);
        System.out.println(getFilePoolByUuid);
        return ResponseEntity.ok().body(getFilePoolByUuid);
    }


    /**
     * Request to delete old file in the MINIO-server`s bucket
     */
    @ApiOperation("send request to upload file to bucjets from source")
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity delete(@PathVariable("fileName") String fileName) {
        log.info("delete file: {}", fileName);
        minioService.delete(fileName);
        log.info("delete file: {}", fileName);
        return ResponseEntity.ok().body("File is deleted");
    }
}
