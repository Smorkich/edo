package com.education.feign;

import model.dto.QuestionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.QUESTION_URL;

@FeignClient(name = EDO_REPOSITORY_NAME, path = QUESTION_URL)
public interface QuestionFeignClient {

    @GetMapping("/{id}")
    QuestionDto findById(@PathVariable Long id);

    @GetMapping("/all")
    Collection<QuestionDto> findAll();

    @GetMapping("/all/{ids}")
    Collection<QuestionDto> findAllById(@PathVariable Collection<Long> ids);

    @PostMapping()
    QuestionDto save(@RequestBody QuestionDto questionDto);

    @DeleteMapping("/{id}")
    HttpStatus delete(@PathVariable("id") Long id);

    @PostMapping("/{id}")
    String moveToArchived(@PathVariable(name = "id") Long id);

    @GetMapping("/notArchived/{id}")
    QuestionDto getFileNotArchived(@PathVariable(name = "id") Long id);

    @GetMapping("/notArchivedAll/{ids}")
    Collection<QuestionDto> getFilesNotArchived(@PathVariable(name = "ids") String ids);

    @PostMapping("/register/{questionId}")
    QuestionDto registerQuestion(@PathVariable("questionId") Long questionId);

    @PostMapping("/registerAll")
    Collection<QuestionDto> registerAllQuestions(@RequestBody Collection<Long> questionsIds);

    @PostMapping("/setStatusUpdated/{questionId}")
    QuestionDto setStatusUpdated(@PathVariable("questionId") Long questionId);

//    @PostMapping("/setStatusUpdatedAll")
//    Collection<QuestionDto> setStatusUpdatedAll(@RequestBody Collection<Long> questionsIds);todo понять, почему не запускается с этим методом

    @GetMapping("/appeal/{id}")
    Collection<QuestionDto> findByAppealId(@PathVariable("id") Long appealId);
}
