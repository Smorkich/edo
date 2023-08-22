package com.education.service.question.impl;

import com.education.entity.Question;
import com.education.repository.question.QuestionRepository;
import com.education.service.question.QuestionService;
import lombok.AllArgsConstructor;
import model.enum_.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Nadezhda Pupina
 * Реализует связь контроллера и репозитория
 */
@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Question save(Question question) {
        question.setCreationDate(ZonedDateTime.now());
        return questionRepository.saveAndFlush(question);
    }

    /**
     * Сохраняет коллекцию Question в БД через repository
     *
     * @param questions коллекция добавляемых Question
     * @return Collection<Question> - коллекция сущности Question (вопросы)
     */
    @Override
    public Collection<Question> saveAll(Collection<Question> questions) {
        return questionRepository.saveAll(questions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    /**
     * Ищет все Question по id и изменяет их поля status на 'REGISTERED' (зарегистрирован),
     * после чего возвращает коллекцию текущих(изменённых) вопросов
     *
     * @param questionsIds - id вопросов, статус которых хотим изменить на 'REGISTERED'
     * @return Collection<Question> - коллекция вопросов, чьи статусы изменяем
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Collection<Question> registerAllQuestions(Iterable<Long> questionsIds) {
        questionRepository.updateAllQuestionStatus(questionsIds,Status.REGISTERED);
        return questionRepository.findAllById(questionsIds);
    }

    /**
     * Ищет Question по id и изменяет его поле status на 'REGISTERED' (зарегистрирован),
     * после чего возвращает текущий(изменённый) вопрос
     *
     * @param questionId - id вопроса, статус которого хотим изменить на 'REGISTERED'
     * @return Question - вопрос, чей статус изменяем
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Question registerQuestion(Long questionId) {
        questionRepository.updateQuestionStatus(questionId, Status.REGISTERED);
        return questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("Question not found"));
    }

    /**
     * Ищет Question по id и изменяет его поле status на 'UPDATED' (изменён),
     * после чего возвращает текущий(изменённый) вопрос
     *
     * @param questionId - id вопроса, статус которого хотим изменить на 'UPDATED'
     * @return Question - вопрос, чей статус изменяем
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Question setStatusUpdated(Long questionId) {
        questionRepository.updateQuestionStatus(questionId, Status.UPDATED);
        return questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("Question not found"));
    }

    /**
     * Ищет все Question по id и изменяет их поля status на 'UPDATED' (изменён),
     * после чего возвращает коллекцию текущих(изменённых) вопросов
     *
     * @param questionsIds - id вопросов, статус которых хотим изменить на 'UPDATED'
     * @return Collection<Question> - коллекция вопросов, чьи статусы изменяем
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Collection<Question> setStatusUpdatedAll(Iterable<Long> questionsIds) {
        questionRepository.updateAllQuestionStatus(questionsIds,Status.UPDATED);
        return questionRepository.findAllById(questionsIds);
    }

    @Override
    @Transactional(readOnly = true)
    public Question findById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findAllById(Iterable<Long> ids) {
        return questionRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveToArchive(Long id) {
        questionRepository.moveToArchive(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Question findByIdAndArchivedDateNull(Long id) {
        return questionRepository.findByIdAndArchivedDateNull(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Question> findByAllIdNotArchived(Collection<Long> ids) {
        return questionRepository.findByIdInAndArchivedDateNull(ids);
    }

    /**
     * Вызывает метод findByAppealId(), который возвращает по id Appeal'a все вопросы относящиеся к нему
     *
     * @param id - id Appeal'a вопросы которого хотим найти
     * @return Collection<Question> - коллекция вопросов Appeal'a
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Question> findByAppealId(Long id) {
        return questionRepository.findByAppealId(id);
    }

}
