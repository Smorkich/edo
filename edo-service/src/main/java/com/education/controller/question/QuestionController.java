package com.education.controller.question;

import com.education.service.question.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.QuestionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author Nadezhda Pupina
 * Rest-контроллер в "edo-service", необходим для отправки запросов от клиента к БД, использует RestTemplate
 */
@Log4j2
@RestController
@AllArgsConstructor
@Tag(name ="Rest-контроллер для работы с вопросами")
@RequestMapping("/api/service/question")
public class QuestionController {

    private final QuestionService questionService;

    //GET ONE /api/service/question/{id}
    @Operation(summary = "Возвращает вопрос по id", description = "Вопрос должен существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private QuestionDto findById(@PathVariable(name = "id") Long id) {
        log.info("Send a get-request to get Question with id = {} from edo-repository (RestTemplate on edo-service side)", id);
        var questionDto = questionService.findById(id);
        log.info("The operation was successful, we got the Question by id ={}", questionDto);
        return questionDto;
    }

    //GET ALL /api/service/question/all
    @Operation(summary = "Возвращает все вопросы", description = "Вопросы должны существовать")
    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<QuestionDto> findAll() {
        log.info("Send a get-request to get all Questions from edo-repository");
        var questionDtos = questionService.findAll();
        log.info("Response from edo-repository - all Questions");
        return questionDtos;
    }

    //GET ALL /api/service/question/allById
    @Operation(summary = "Возвращает все вопросы", description = "Вопросы должны существовать")
    @PostMapping(value = "/allById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<QuestionDto>> findByAllId(@RequestBody Collection<Long> ids) {
        log.info("Send a get-request to get all Questions from edo-repository (RestTemplate on edo-service side)");
        var questionDtos = questionService.findAllById(ids);
        log.info("Response from edo-repository: {}", ids);
        return new ResponseEntity<>(questionDtos, HttpStatus.OK);
    }

    //POST /api/service/question
    @Operation(summary = "Создает вопрос в БД")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus save(@RequestBody QuestionDto questionDto) {
        log.info("Send a post-request to edo-repository to post new Question to database");
        questionService.save(questionDto);
        log.info("Response: {} was added to database", questionDto);
        return HttpStatus.CREATED;
    }

    /**
     * Принимает запрос на создание вопросов(Question), которые передаются в теле HTTP запроса
     * <p>Вызывает метод saveAll() из интерфейса QuestionService, микросервиса edo-service
     *
     * @param questionDtos добавляемые QuestionDto
     * @return ResponseEntity<Collection < QuestionDto> - ResponseEntity коллекции DTO сущности Question (вопросы обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Создает вопросы в БД")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<QuestionDto>> saveAll(@RequestBody Collection<QuestionDto> questionDtos) {
        log.info("Send a post-request to edo-repository to post new Questions to database (RestTemplate on edo-service side)");
        questionService.saveAll(questionDtos);
        log.info("Response: {} was added to database", questionDtos);
        return new ResponseEntity<>(questionDtos, HttpStatus.CREATED);
    }

    //DELETE /api/service/question/{id}
    @Operation(summary = "Удаляет вопрос из БД", description = "Вопрос должен существовать")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus delete(@PathVariable("id") Long id) {
        log.info("Send a delete-request to edo-repository to delete Question with id = {} from database (RestTemplate on edo-service side)", id);
        questionService.delete(id);
        log.info("Response: Question with id = {} was deleted from database", id);
        return HttpStatus.ACCEPTED;
    }

    //POST /api/service/question/{id}
    @Operation(summary = "Добавление в вопрос времени архивации")
    @PostMapping("/{id}")
    private String moveToArchived(@PathVariable(name = "id") Long id) {
        log.info("Starting the archiving operation");
        questionService.moveToArchived(id);
        log.info("Archived date added to question");
        return "Archived date added to question";
    }

    //GET ONE WITHOUT ARCHIVED DATE /api/service/question/notArchived/{id}
    @Operation(summary = "Предоставление вопроса без даты архивации по id")
    @GetMapping("/notArchived/{id}")
    private QuestionDto findByIdNotArchived(@PathVariable(name = "id") Long id) {
        log.info("send a response with the Question not archived of the assigned id");
        var questionDto = questionService.findByIdNotArchived(id);
        log.info("The operation was successful, they got the non-archived Question by id ={}", id);
        return questionDto;
    }

    //GET ALL WITHOUT ARCHIVED DATE /api/service/question/notArchivedAll/{id}
    @Operation(summary = "Предоставление вопросов без архивации по IDs")
    @GetMapping("/notArchivedAll/{ids}")
    private Collection<QuestionDto> findByAllIdNotArchived(@PathVariable(name = "ids") String ids) {
        log.info("send a response with the Question not archived of the assigned IDs");
        var questionDtos = questionService.findByAllIdNotArchived(ids);
        log.info("The operation was successful, they got the non-archived Question by id ={}", ids);
        return questionDtos;
    }

    /**
     * Принимает запрос на регистрацию вопроса(Question) по id, который передаётся в параметре HTTP запроса
     * <p>Вызывает метод registerQuestion() из интерфейса QuestionService, микросервиса edo-service
     *
     * @param id идентификатор регистрируемого Question
     * @return ResponseEntity<QuestionDto> - ResponseEntity DTO сущности Question (вопрос обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Регистрирует вопрос по id", description = "Вопрос должен существовать")
    @PostMapping(value = "/register/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<QuestionDto> registerById(@PathVariable(name = "id") Long id) {
        log.info("Send a post-request to register Question with id = {} from edo-repository (RestTemplate on edo-service side)", id);
        var questionDto = questionService.registerQuestion(id);
        log.info("The operation was successful,the Question by id ={} has been registered", questionDto);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

    /**
     * Принимает запрос на регистрацию вопросов(Question) по ids, список ids передаётся в параметре HTTP запроса
     * <p>Вызывает метод registerAllQuestions() из интерфейса QuestionService, микросервиса edo-service
     *
     * @param ids идентификатор регистрируемого Question
     * @return ResponseEntity<QuestionDto> - ResponseEntity DTO сущности Question (вопрос обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Регистрирует все вопросы по id", description = "Вопросы должны существовать")
    @PostMapping(value = "/registerAll/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<QuestionDto>> registerAllById(@PathVariable(name = "ids") Iterable<Long> ids) {
        log.info("Send a post-request to register all Questions from edo-repository (RestTemplate on edo-service side)");
        var questionDtos = questionService.registerAllQuestions(ids);
        log.info("The operation was successful, the Questions has been registered");
        return new ResponseEntity<>(questionDtos, HttpStatus.OK);
    }

    /**
     * Принимает запрос на изменение статуса вопроса(Question) по id на 'UPDATED', id передаётся в параметре HTTP запроса
     * <p>Вызывает метод setStatusUpdated() из интерфейса QuestionService, микросервиса edo-service
     *
     * @param id идентификатор изменяемого Question
     * @return ResponseEntity<QuestionDto> - ResponseEntity DTO сущности Question (вопрос обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Изменяет статус вопроса по id на 'UPDATED'", description = "Вопрос должен существовать")
    @PostMapping(value = "/setUpdatedStatus/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<QuestionDto> setUpdatedStatusById(@PathVariable(name = "id") Long id) {
        log.info("Send a post-request to change Question status on 'UPDATED' from edo-service with id = {}", id);
        var questionDto = questionService.setStatusUpdated(id);
        log.info("Success, status of the Question by id ={} has been changed to 'UPDATED'", questionDto);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

    /**
     * Принимает запрос на изменение статуса вопросов(Question) по id на 'UPDATED', id передаются в параметре HTTP запроса
     * <p>Вызывает метод setStatusUpdatedAll() из интерфейса QuestionService, микросервиса edo-service
     *
     * @param ids идентификаторы изменяемых Question
     * @return ResponseEntity<Collection < QuestionDto> - коллекция ResponseEntity DTO сущности Question (вопросы обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Изменяет статусы вопросов по id на 'UPDATED'", description = "Вопросы должны существовать")
    @PostMapping(value = "/setUpdatedStatusAll/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<QuestionDto>> setUpdatedStatusAllByID(@PathVariable(name = "ids") Iterable<Long> ids) {
        log.info("Send a post-request to change list of Question status on 'UPDATED' from edo-service");
        var questionDtos = questionService.setStatusUpdatedAll(ids);
        log.info("The operation was successful,status of the Questions has been changed to 'UPDATED'");
        return new ResponseEntity<>(questionDtos, HttpStatus.OK);
    }

}
