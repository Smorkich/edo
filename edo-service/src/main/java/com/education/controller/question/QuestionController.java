package com.education.controller.question;

import com.education.service.question.QuestionService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.QuestionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author Nadezhda Pupina
 * Rest-контроллер в "edo-service", необходим для отправки запросов от клиента к БД, использует RestTemplate
 */
@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/service/question")
public class QuestionController {

    private final QuestionService questionService;

    //GET ONE /api/service/question/{id}
    @ApiOperation(value = "Возвращает вопрос по id", notes = "Вопрос должен существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private QuestionDto findById(@PathVariable(name = "id") Long id) {
        log.info("Send a get-request to get Question with id = {} from edo-repository (RestTemplate on edo-service side)", id);
        var questionDto = questionService.findById(id);
        log.info("The operation was successful, we got the Question by id ={}", questionDto);
        return questionDto;
    }

    //GET ALL /api/service/question/all
    @ApiOperation(value = "Возвращает все вопросы", notes = "Вопросы должны существовать")
    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<QuestionDto> findAll() {
        log.info("Send a get-request to get all Questions from edo-repository");
        var questionDtos = questionService.findAll();
        log.info("Response from edo-repository - all Questions");
        return questionDtos;
    }

    @ApiOperation(value = "Возвращает все вопросы по ids", notes = "Вопросы должны существовать")
    @GetMapping(value = "all/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<QuestionDto> findAll(@PathVariable("ids") List<Long> ids) {
        log.info("Send a get-request to get all Questions from edo-repository by ids = {}", ids);
        var questionDtos = questionService.findAllById(ids);
        log.info("Response from edo-repository - Questions with id: {}", ids);
        return questionDtos;
    }

    //POST /api/service/question
    @ApiOperation(value = "Создает вопрос в БД")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus save(@RequestBody QuestionDto questionDto) {
        log.info("Send a post-request to edo-repository to post new Question to database");
        questionService.save(questionDto);
        log.info("Response: {} was added to database", questionDto);
        return HttpStatus.CREATED;
    }

    //DELETE /api/service/question/{id}
    @ApiOperation(value = "Удаляет вопрос из БД", notes = "Вопрос должен существовать")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus delete(@PathVariable("id") Long id) {
        log.info("Send a delete-request to edo-repository to delete Question with id = {} from database (RestTemplate on edo-service side)", id);
        questionService.delete(id);
        log.info("Response: Question with id = {} was deleted from database", id);
        return HttpStatus.ACCEPTED;
    }

    //POST /api/service/question/{id}
    @ApiOperation(value = "Добавление в вопрос времени архивации")
    @PostMapping("/{id}")
    private String moveToArchived(@PathVariable(name = "id") Long id) {
        log.info("Starting the archiving operation");
        questionService.moveToArchived(id);
        log.info("Archived date added to question");
        return "Archived date added to question";
    }

    //GET ONE WITHOUT ARCHIVED DATE /api/service/question/notArchived/{id}
    @ApiOperation(value = "Предоставление вопроса без даты архивации по id")
    @GetMapping("/notArchived/{id}")
    private QuestionDto findByIdNotArchived(@PathVariable(name = "id") Long id) {
        log.info("send a response with the Question not archived of the assigned id");
        var questionDto = questionService.findByIdNotArchived(id);
        log.info("The operation was successful, they got the non-archived Question by id ={}", id);
        return questionDto;
    }

    //GET ALL WITHOUT ARCHIVED DATE /api/service/question/notArchivedAll/{id}
    @ApiOperation(value = "Предоставление вопросов без архивации по ids")
    @GetMapping("/notArchivedAll/{ids}")
    private Collection<QuestionDto> findByAllIdNotArchived(@PathVariable(name = "ids") String ids) {
        log.info("send a response with the Question not archived of the assigned IDs");
        var questionDtos = questionService.findByAllIdNotArchived(ids);
        log.info("The operation was successful, they got the non-archived Question by id ={}", ids);
        return questionDtos;
    }

}
