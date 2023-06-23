package com.education.feign;

import jakarta.validation.Valid;
import model.dto.FilePoolDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.FILEPOOL_URL;

@FeignClient(name = EDO_REPOSITORY_NAME)
public interface FilePoolFeignClient {

    @PostMapping(FILEPOOL_URL)
    ResponseEntity<FilePoolDto> save(@RequestBody @Valid FilePoolDto filePoolDto);

    @GetMapping(FILEPOOL_URL + "/{id}")
    ResponseEntity<FilePoolDto> findById(@PathVariable Long id);

    @DeleteMapping(FILEPOOL_URL + "/{id}")
    ResponseEntity<FilePoolDto> delete(@PathVariable Long id);

    @GetMapping(FILEPOOL_URL + "/info/{uuid}")
    ResponseEntity<FilePoolDto> findByUuid(@PathVariable UUID uuid);

    @GetMapping(FILEPOOL_URL)
    ResponseEntity<Collection<FilePoolDto>> findAll();

    @PostMapping(FILEPOOL_URL + "/{id}")
    ResponseEntity<String> moveToArchive(@PathVariable(name = "id") Long id);

    @GetMapping(FILEPOOL_URL + "/noArchived/{id}")
    ResponseEntity<FilePoolDto> getFileNotArchived(@PathVariable Long id);

    @GetMapping(FILEPOOL_URL + "/noArchived/{ids}")
    ResponseEntity<Collection<FilePoolDto>> getFilesNotArchived(@PathVariable String ids);
}
