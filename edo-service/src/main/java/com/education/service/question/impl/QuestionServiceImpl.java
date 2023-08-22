package com.education.service.question.impl;

import com.education.feign.QuestionFeignClient;
import com.education.service.question.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.QuestionDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Stream;

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
        return Stream.ofNullable(questionDtos)
                .flatMap(Collection::stream)
                .map(this::save)
                .toList();
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
        return questionFeignClient.registerQuestion(questionId);
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
        return questionFeignClient.registerAllQuestions(questionsIds);
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
        return questionFeignClient.setStatusUpdated(questionId);
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
        return questionFeignClient.setStatusUpdatedAll(questionsIds);
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
        return questionFeignClient.findByAppealId(id);
    }
}
