package com.education.controller.question;

import com.education.service.question.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.QuestionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private QuestionDto findById(@PathVariable(name = "id") Long id) {
        log.info("Send a get-request to get Question with id = {} from database", id);
        var questionDto = QUESTION_MAPPER.toDto(questionService.findById(id));
        log.info("The operation was successful, we got the Question by id ={}", id);
        return questionDto;
    }

    //GET ALL /api/repository/question/all/
    @Operation(summary = "Возвращает все вопросы", description = "Вопросы должны существовать")
    @GetMapping("/all")
    private List<QuestionDto> findAll() {
        log.info("Send a get-request to get all Question from database");
        var questionDtos = (List<QuestionDto>) QUESTION_MAPPER.toDto(questionService.findAll());
        log.info("The operation was successful, all non-archived Question has been received");
        return questionDtos;
    }


    //GET ALL /api/repository/question/all/{ids}
    @Operation(summary = "Возвращает все вопросы", description = "Вопросы должны существовать")
    @GetMapping("/all/{ids}")
    private List<QuestionDto> findAll(@PathVariable(name = "ids") List<Long> ids) {
        log.info("Send a get-request to get all Question by ids from database");
        var questionDtos = (List<QuestionDto>) QUESTION_MAPPER.toDto(questionService.findAllById(ids));
        log.info("The operation was successful, they got the non-archived Question by id ={}", ids);
        return questionDtos;
    }

    //POST /api/repository/question
    @Operation(summary = "Создает вопрос в БД", description = "Вопрос не должен существовать")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionDto save(@RequestBody QuestionDto questionDto) {
        log.info("Send a post-request to post new Question to database");
        var saved = questionService.save(QUESTION_MAPPER.toEntity(questionDto));
        log.info("Response: {} was added to database", questionDto);
        return QUESTION_MAPPER.toDto(saved);
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
    public Collection<QuestionDto> saveAll(@RequestBody Collection<QuestionDto> questionDtos) {
        log.info("Send a query to repository to post new Questions to database");
        var savedAll = questionService.saveAll(QUESTION_MAPPER.toEntity(questionDtos));
        log.info("Response: {} was added to database", questionDtos);
        return QUESTION_MAPPER.toDto(savedAll);
    }

    //DELETE /api/repository/question/{id}
    @Operation(summary = "Удаляет вопрос из БД", description = "Вопрос должен существовать")
    @DeleteMapping(value = "/{id}")
    public HttpStatus delete(@PathVariable("id") Long id) {
        log.info("Send a delete-request to delete Question with id = {} from database", id);
        questionService.delete(questionService.findById(id));
        log.info("Response: Question with id = {} was deleted from database", id);
        return HttpStatus.ACCEPTED;
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
    private QuestionDto registerById(@PathVariable(name = "id") Long id) {
        log.info("Send a post-request to change Question status on 'REGISTERED' from edo-repository with id = {}", id);
        var questionDto = questionService.registerQuestion(id);
        return QUESTION_MAPPER.toDto(questionDto);
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
    public Collection<QuestionDto> registerAllById(@RequestBody Collection<Long> ids) {
        log.info("Send a post-request to change list of Question status on 'REGISTERED' from edo-repository");
        var questionDtos = questionService.registerAllQuestions(ids);
        log.info("The operation was successful,status of the Questions has been changed to 'REGISTERED'");
        return QUESTION_MAPPER.toDto(questionDtos);
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
    private QuestionDto setStatusUpdatedById(@PathVariable(name = "id") Long id) {
        log.info("Send a post-request to change Question status on 'UPDATED' from edo-repository with id = {}", id);
        var questionDto = questionService.setStatusUpdated(id);
        return QUESTION_MAPPER.toDto(questionDto);
    }

    /**
     * Принимает запрос на изменение статуса вопросов по id на "UPDATED", id передаются в параметре HTTP запроса
     * <p>Вызывает метод setStatusUpdatedAll() из интерфейса QuestionService, микросервиса edo-repository
     *
     * @param ids идентификаторы Question's status которых хотим изменить на "UPDATED"
     * @return ResponseEntity коллекции DTO сущностей Question (вопросы обращения)
     * @apiNote HTTP Method - POST
     */
    @Operation(summary = "Изменяет статус вопросов на 'UPDATED' по id", description = "Вопросы должны существовать")
    @PostMapping(value = "/setStatusUpdatedAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<QuestionDto> setStatusUpdatedAllById(@RequestBody Collection<Long> ids) {
        log.info("Send a post-request to change list of Question status on 'UPDATED' from edo-repository");
        var questionDtos = questionService.setStatusUpdatedAll(ids);
        log.info("The operation was successful,status of the Questions has been changed to 'UPDATED'");
        return QUESTION_MAPPER.toDto(questionDtos);
    }

    //POST /api/repository/question/move/{id}
    @Operation(summary = "Добавляет в вопрос дату архивации", description = "Вопрос должен существовать")
    @PostMapping("/{id}")
    private QuestionDto moveToArchived(@PathVariable(name = "id") Long id) {
        log.info("Starting the archiving operation");
        questionService.moveToArchive(id);
        log.info("Added archiving date");
        return QUESTION_MAPPER.toDto(questionService.findById(id));
    }

    //GET ONE WITHOUT ARCHIVED DATE /api/repository/question/notArchived/{id}
    @Operation(summary = "Возвращает вопрос без архивации по id", description = "Вопрос должен существовать")
    @GetMapping("/notArchived/{id}")
    private QuestionDto findByIdAndArchivedDateNull(@PathVariable(name = "id") Long id) {
        log.info("send a response with the Question not archived of the assigned ID");
        QuestionDto questionDto = QUESTION_MAPPER.toDto(questionService.findByIdAndArchivedDateNull(id));
        log.info("The operation was successful, they got the non-archived Question by id ={}", id);
        return questionDto;
    }

    //GET ALL WITHOUT ARCHIVED DATE /api/repository/question/notArchivedAll/{ids}
    @Operation(summary = "Возвращает вопросы без архивации по IDs", description = "Вопросы должны существовать")
    @GetMapping("/notArchivedAll/{ids}")
    private List<QuestionDto> findByAllIdNotArchived(@PathVariable(name = "ids") List<Long> ids) {
        log.info("send a response with the Question not archived of the assigned IDs");
        List<QuestionDto> questionDto = (List<QuestionDto>) QUESTION_MAPPER.toDto(questionService.findByAllIdNotArchived(ids));
        log.info("The operation was successful, they got the non-archived Question by id ={}", ids);
        return questionDto;
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
    private List<QuestionDto> findByAppeal(@PathVariable(name = "id") Long id) {
        log.info("Запрос на получение вопросов из обращения");
        List<QuestionDto> questionDto = (List<QuestionDto>) QUESTION_MAPPER.toDto(questionService.findByAppealId(id));
        log.info("Запрос прошёл успешно, получены все вопросы по Appeal id ={}", id);
        return questionDto;
    }

}
