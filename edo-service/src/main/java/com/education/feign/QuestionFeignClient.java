package com.education.feign;

import model.dto.QuestionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.QUESTION_URL;

@FeignClient(name = EDO_REPOSITORY_NAME)
public interface QuestionFeignClient {

    @GetMapping(QUESTION_URL + "/{id}")
    QuestionDto findById(@PathVariable Long id);

    @GetMapping(QUESTION_URL + "/all")
    Collection<QuestionDto> findAll();

    @GetMapping(QUESTION_URL + "/all/{ids}")
    Collection<QuestionDto> findAllById(@PathVariable Iterable<Long> ids);

    @PostMapping(QUESTION_URL)
    QuestionDto save(@RequestBody QuestionDto questionDto);

    @DeleteMapping(QUESTION_URL + "/{id}")
    HttpStatus delete(@PathVariable("id") Long id);

    @PostMapping(QUESTION_URL + "/{id}")
    String moveToArchived(@PathVariable(name = "id") Long id);

    @GetMapping(QUESTION_URL + "/notArchived/{id}")
    QuestionDto getFileNotArchived(@PathVariable(name = "id") Long id);

    @GetMapping(QUESTION_URL + "/notArchivedAll/{ids}")
    Collection<QuestionDto> getFilesNotArchived(@PathVariable(name = "ids") String ids);

}
