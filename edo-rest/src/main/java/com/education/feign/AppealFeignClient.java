package com.education.feign;

import model.dto.AppealDto;
import model.dto.FilePoolDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static model.constant.Constant.*;

@FeignClient(name = EDO_SERVICE_NAME, url = SERVICE_APPEAL_URL)
public interface AppealFeignClient extends BaseRestFeignClient<AppealDto> {
    @PutMapping("/move/{id}")
    void moveToArchive(@PathVariable("id") Long id);

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<AppealDto> uploadFile(@RequestParam("id") Long id, @RequestParam("file") FilePoolDto file);
    @GetMapping("/findByIdNotArchived/{id}")
    AppealDto findByIdNotArchived(@PathVariable("id") Long id);

    @GetMapping("/findByIdNotArchived")
    Collection<AppealDto> findAllNotArchived();

    @GetMapping("/findAppealByQuestionsId/{id}")
    AppealDto findAppealByQuestionsId(@PathVariable("id") Long id);

    @PostMapping("/register")
    AppealDto registerAppeal(@RequestParam("id") Long id);
}
