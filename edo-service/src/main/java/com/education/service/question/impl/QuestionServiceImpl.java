package com.education.service.question.impl;

import com.education.feign.QuestionFeignClient;
import com.education.service.question.QuestionService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.QuestionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.stream.Stream;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.QUESTION_URL;

/**
 * @author Nadezhda Pupina
 * Service в "edo-service", создает связь контроллера и RestTemplate
 */
@Service
@AllArgsConstructor
@Log4j2
public class QuestionServiceImpl implements QuestionService {

    private final QuestionFeignClient questionFeignClient;

    @Override
    public QuestionDto save(QuestionDto questionDto) {
        return questionFeignClient.save(questionDto);
    }

    /**
     * Сохраняет коллекцию QuestionDto, которая передаётся в параметр - отправляет запрос в контроллер edo-repository
     *
     * @param questionDtos коллекция добавляемых QuestionDto
     * @return Collection<QuestionDto> - коллекция DTO сущности QuestionDto (вопросы обращения)
     */
    @Override
    public Collection<QuestionDto> saveAll(Collection<QuestionDto> questionDtos) {
        var dtos = Stream.ofNullable(questionDtos)
                .flatMap(Collection::stream)
                .map(this::save).toList();
        var uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, QUESTION_URL + "/all");
        HttpEntity<Collection<QuestionDto>> httpEntity = new HttpEntity<>(dtos);
        ParameterizedTypeReference<Collection<QuestionDto>> responseType = new ParameterizedTypeReference<>() {
        };
        return restTemplate.exchange(uri.toString(), HttpMethod.POST, httpEntity, responseType).getBody();
    }

    @Override
    public void delete(long id) {
        questionFeignClient.delete(id);
    }

    @Override
    public QuestionDto findById(long id) {
        return questionFeignClient.findById(id);
    }

    /**
     * Составляет логику добавления статуса REGISTERED (зарегистрировано) вопросу по id из параметра HTTP запроса.
     * <p>Отправляет запрос в edo-repository на добавление статуса REGISTERED Question по id,
     * id передаётся в параметре
     *
     * @param questionId идентификатор регистрируемого Question
     * @return QuestionDto - DTO сущности Question (вопрос обращения)
     */
    @Override
    public QuestionDto registerQuestion(Long questionId) {
        var uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/question/register/" + questionId);
        return restTemplate.postForObject(uri.toString(), new HttpEntity<>(new HttpHeaders()), QuestionDto.class);
    }

    /**
     * Составляет логику добавления статуса REGISTERED (зарегистрировано) вопросу по id из параметра
     * <p>Отправляет запрос в edo-repository на добавление статуса REGISTERED коллекции Question по ids,
     * ids передаются в параметре как "Iterable"
     *
     * @param questionsIds идентификаторы регистрируемых Question
     * @return Collection<QuestionDto> - коллекция из DTO сущности Question (вопросов обращения)
     */
    @Override
    public Collection<QuestionDto> registerAllQuestions(Iterable<Long> questionsIds) {
        var uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/question/registerAll");
        HttpEntity<Iterable<Long>> httpEntity = new HttpEntity<>(questionsIds, new HttpHeaders());
        return restTemplate.exchange(uri.toString(), HttpMethod.POST, httpEntity, responseType).getBody();
    }

    /**
     * Составляет логику добавления статуса UPDATED (внесены изменения) вопросу по id из параметра HTTP запроса.
     * <p>Отправляет запрос в edo-repository на добавление статуса UPDATED Question по id,
     * id передаётся в параметре
     *
     * @param questionId идентификатор изменяемого Question
     * @return QuestionDto - DTO сущности Question (вопрос обращения)
     */
    @Override
    public QuestionDto setStatusUpdated(Long questionId) {
        var uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/question/setStatusUpdated/" + questionId);
        return restTemplate.postForObject(uri.toString(), new HttpEntity<>(new HttpHeaders()), QuestionDto.class);
    }

    /**
     * Составляет логику добавления статуса UPDATED (внесены изменения) вопросам по id из параметра
     * <p>Отправляет запрос в edo-repository на добавление статуса UPDATED коллекции Question по ids,
     * ids передаются в параметре как "Iterable"
     *
     * @param questionsIds идентификаторы изменяемых Question
     * @return Collection<QuestionDto> - коллекция из DTO сущности Question (вопросов обращения)
     */
    @Override
    public Collection<QuestionDto> setStatusUpdatedAll(Iterable<Long> questionsIds) {
        var uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/question/setStatusUpdatedAll");
        HttpEntity<Iterable<Long>> httpEntity = new HttpEntity<>(questionsIds, new HttpHeaders());
        return restTemplate.exchange(uri.toString(), HttpMethod.POST, httpEntity, responseType).getBody();
    }

    @Override
    public Collection<QuestionDto> findAll() {
        return questionFeignClient.findAll();

    }

    @Override
    public Collection<QuestionDto> findAllById(Iterable<Long> ids) {
        return questionFeignClient.findAllById(ids);
    }

    @Override
    public void moveToArchived(Long id) {
        questionFeignClient.moveToArchived(id);
    }

    @Override
    public QuestionDto findByIdNotArchived(Long id) {
        return questionFeignClient.getFileNotArchived(id);
    }

    @Override
    public Collection<QuestionDto> findByAllIdNotArchived(String ids) {
        return questionFeignClient.getFilesNotArchived(ids);
    }

    @Override
    public Collection<QuestionDto> findByAppealId(Long id) {
        var uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/question/appeal/" + id);
        return restTemplate.exchange(uri.toString(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), responseType).getBody();
    }

}
