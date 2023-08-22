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
import java.util.List;

import static com.education.mapper.QuestionMapper.QUESTION_MAPPER;

/**
 * @author Nadezhda Pupina
 * Rest-контроллер в "edo-repository", необходим для отправки запросов от клиента к БД
 */
@Log4j2
@RestController
@AllArgsConstructor
@Tag(name = "Rest-контроллер для работы с вопросами")
@RequestMapping("/api/repository/question")
public class QuestionController {

    private final QuestionService questionService;

    //GET ONE /api/repository/question/{id}
    @Operation(summary = "Возвращает вопрос по id", description = "Вопрос должен существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<QuestionDto> findById(@PathVariable(name = "id") Long id) {
        log.info("Send a get-request to get Question with id = {} from database", id);
        var questionDto = QUESTION_MAPPER.toDto(questionService.findById(id));
        log.info("The operation was successful, we got the Question by id ={}", id);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

    //GET ALL /api/repository/question/all/{ids}
    @Operation(summary = "Возвращает все вопросы", description = "Вопросы должны существовать")
    @GetMapping("/all/{ids}")
    private ResponseEntity<List<QuestionDto>> findAll(@PathVariable(name = "ids") List<Long> ids) {
        log.info("Send a get-request to get all Question from database");
        var questionDtos = (List<QuestionDto>) QUESTION_MAPPER.toDto(questionService.findAll());
        log.info("The operation was successful, they got the non-archived Question by id ={}", ids);
        return new ResponseEntity<>(questionDtos, HttpStatus.OK);
    }

    //POST /api/repository/question
    @Operation(summary = "Создает вопрос в БД", description = "Вопрос не должен существовать")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionDto> save(@RequestBody QuestionDto questionDto) {
        log.info("Send a post-request to post new Question to database");
        var saved = questionService.save(QUESTION_MAPPER.toEntity(questionDto));
        log.info("Response: {} was added to database", questionDto);
        return new ResponseEntity<>(QUESTION_MAPPER.toDto(saved), HttpStatus.CREATED);
    }

    /**
     * Принимает запрос на создание вопросов в БД, которые передаются в теле HTTP запроса
     * <p>Вызывает метод saveAll() из интерфейса QuestionService, микросервиса edo-repository
     *
     * @param questionDtos добавляемые QuestionDto
     * @return ResponseEntity<Collection < QuestionDto> - ResponseEntity коллекции DTO сущности Question (вопросы обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Создает вопросы в БД")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<QuestionDto>> saveAll(@RequestBody Collection<QuestionDto> questionDtos) {
        log.info("Send a query to repository to post new Questions to database");
        var savedAll = questionService.saveAll(QUESTION_MAPPER.toEntity(questionDtos));
        log.info("Response: {} was added to database", questionDtos);
        return new ResponseEntity<>(QUESTION_MAPPER.toDto(savedAll), HttpStatus.CREATED);
    }

    //DELETE /api/repository/question/{id}
    @Operation(summary = "Удаляет вопрос из БД", description = "Вопрос должен существовать")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") long id) {
        log.info("Send a delete-request to delete Question with id = {} from database", id);
        questionService.delete(questionService.findById(id));
        log.info("Response: Question with id = {} was deleted from database", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Принимает запрос на изменение статуса вопроса по id на "REGISTERED", id передаётся в параметре HTTP запроса
     * <p>Вызывает метод registerQuestion() из интерфейса QuestionService, микросервиса edo-repository
     *
     * @param id идентификатор регистрируемого Question
     * @return ResponseEntity<QuestionDto> - ResponseEntity DTO сущности Question (вопрос обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Изменяет статус вопросов на 'REGISTERED", description = "Вопрос должен существовать")
    @PostMapping(value = "/register/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<QuestionDto> registerById(@PathVariable(name = "id") Long id) {
        log.info("Send a post-request to change Question status on 'REGISTERED' from edo-repository with id = {}", id);
        var questionDto = questionService.registerQuestion(id);
        log.info("Success, status of the Question by id ={} has been changed to 'REGISTERED'", questionDto);
        return new ResponseEntity<>(QUESTION_MAPPER.toDto(questionDto), HttpStatus.OK);
    }

    /**
     * Принимает запрос на изменение статуса вопросов по id на "REGISTERED", id передаются в параметре HTTP запроса
     * <p>Вызывает метод registerQuestion() из интерфейса QuestionService, микросервиса edo-repository
     *
     * @param ids идентификаторы регистрируемых Question
     * @return ResponseEntity коллекции DTO сущностей Question (вопросы обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Изменяет статус вопросов на 'REGISTERED' по id", description = "Вопросы должны существовать")
    @PostMapping(value = "/registerAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<QuestionDto>> registerAllById(@RequestBody Iterable<Long> ids) {
        log.info("Send a post-request to change list of Question status on 'REGISTERED' from edo-repository");
        var questionDtos = questionService.registerAllQuestions(ids);
        log.info("The operation was successful,status of the Questions has been changed to 'REGISTERED'");
        return new ResponseEntity<>(QUESTION_MAPPER.toDto(questionDtos), HttpStatus.OK);
    }

    /**
     * Принимает запрос на изменение статуса вопроса по id на "UPDATED", id передаётся в параметре HTTP запроса
     * <p>Вызывает метод setStatusUpdated() из интерфейса QuestionService, микросервиса edo-repository
     *
     * @param id идентификатор Question'a status которого хотим изменить на "UPDATED"
     * @return ResponseEntity<QuestionDto> - ResponseEntity DTO сущности Question (вопрос обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Изменяет статус вопроса на 'UPDATED' по id", description = "Вопрос должен существовать")
    @PostMapping(value = "/setStatusUpdated/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<QuestionDto> setStatusUpdatedById(@PathVariable(name = "id") Long id) {
        log.info("Send a post-request to change Question status on 'UPDATED' from edo-repository with id = {}", id);
        var questionDto = questionService.setStatusUpdated(id);
        log.info("Success, status of the Question by id ={} has been changed to 'UPDATED'", questionDto);
        return new ResponseEntity<>(QUESTION_MAPPER.toDto(questionDto), HttpStatus.OK);
    }

    /**
     * Принимает запрос на изменение статуса вопросов по id на "UPDATED", id передаются в параметре HTTP запроса
     * <p>Вызывает метод setStatusUpdatedAll() из интерфейса QuestionService, микросервиса edo-repository
     *
     * @param ids идентификаторы Question's status которых хотим изменить на "UPDATED"
     * @return ResponseEntity коллекции DTO сущностей Question (вопросы обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Изменяет статус вопросов на 'UPDATED' по id", description= "Вопросы должны существовать")
    @PostMapping(value = "/setStatusUpdatedAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<QuestionDto>> setStatusUpdatedAllById(@RequestBody Iterable<Long> ids) {
        log.info("Send a post-request to change list of Question status on 'UPDATED' from edo-repository");
        var questionDtos = questionService.setStatusUpdatedAll(ids);
        log.info("The operation was successful,status of the Questions has been changed to 'UPDATED'");
        return new ResponseEntity<>(QUESTION_MAPPER.toDto(questionDtos), HttpStatus.OK);
    }

    //POST /api/repository/question/move/{id}
    @Operation(summary = "Добавляет в вопрос дату архивации", description = "Вопрос должен существовать")
    @PostMapping("/move/{id}")
    private ResponseEntity<QuestionDto> moveToArchive(@PathVariable(name = "id") Long id) {
        log.info("Starting the archiving operation");
        questionService.moveToArchive(id);
        log.info("Added archiving date");
        QuestionDto questionDto = QUESTION_MAPPER.toDto(questionService.findById(id));
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

    //GET ONE WITHOUT ARCHIVED DATE /api/repository/question/notArchived/{id}
    @Operation(summary = "Возвращает вопрос без архивации по id", description = "Вопрос должен существовать")
    @GetMapping("/notArchived/{id}")
    private ResponseEntity<QuestionDto> findByIdAndArchivedDateNull(@PathVariable(name = "id") Long id) {
        log.info("send a response with the Question not archived of the assigned ID");
        QuestionDto questionDto = QUESTION_MAPPER.toDto(questionService.findByIdAndArchivedDateNull(id));
        log.info("The operation was successful, they got the non-archived Question by id ={}", id);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

    //GET ALL WITHOUT ARCHIVED DATE /api/repository/question/notArchivedAll/{ids}
    @Operation(summary = "Возвращает вопросы без архивации по IDs", description = "Вопросы должны существовать")
    @GetMapping("/notArchivedAll/{ids}")
    private ResponseEntity<List<QuestionDto>> findByAllIdNotArchived(@PathVariable(name = "ids") List<Long> ids) {
        log.info("send a response with the Question not archived of the assigned IDs");
        List<QuestionDto> questionDto = (List<QuestionDto>) QUESTION_MAPPER.toDto(questionService.findByAllIdNotArchived(ids));
        log.info("The operation was successful, they got the non-archived Question by id ={}", ids);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

    /**
     * Принимает запрос на получение вопросов по id Appeal'a, id передаются в параметре HTTP запроса
     * <p>Вызывает метод findByAppealId() из интерфейса QuestionService, микросервиса edo-repository
     *
     * @param id идентификатор Appeal'a, вопросы которого хотим получить
     * @return ResponseEntity списка DTO сущностей Question (вопросы обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Возвращает вопросы по обращению (по id сущности Appeal)", description = "Вопросы должны существовать")
    @GetMapping("/appeal/{id}")
    private ResponseEntity<List<QuestionDto>> findByAppeal(@PathVariable(name = "id") Long id) {
        log.info("Запрос на получение вопросов из обращения");
        List<QuestionDto> questionDto = (List<QuestionDto>) QUESTION_MAPPER.toDto(questionService.findByAppealId(id));
        log.info("Запрос прошёл успешно, получены все вопросы по Appeal id ={}", id);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

}
