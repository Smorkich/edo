package com.education.service.question;

import com.education.entity.Question;

import java.util.Collection;

/**
 * @author Nadezhda Pupina
 * Создает связь контроллера и репозитория
 */
public interface QuestionService {

    /**
     * Метод сохранения нового адреса в БД
     */
    Question save(Question question);

    /**
     * Интерфейс метода, который сохраняет коллекцию Question в БД через repository
     *
     * @param questions коллекция добавляемых Question
     * @return Collection<Question> - коллекция сущности Question (вопросы)
     */
    Collection<Question> saveAll(Collection<Question> questions);

    /**
     * Метод удаления адреса из БД
     */
    void delete(Question question);

    /**
     * Интерфейс для метода, который ищет несколько Question по id и изменяет их поля status на 'REGISTERED'
     *
     * @param questionsIds идентификаторы вопросов, статус которых хотим изменить на 'REGISTERED'
     * @return Collection<QuestionDto> - коллекция DTO сущности Question (вопросы обращения)
     */
    Collection<Question> registerAllQuestions(Iterable<Long> questionsIds);

    /**
     * Интерфейс для метода, который ищет Question по id и изменяет его поле status на 'REGISTERED'
     *
     * @param questionId идентификатор вопроса, статус которого хотим изменить на 'REGISTERED'
     * @return QuestionDto - DTO сущности Question (вопрос обращения)
     */
    Question registerQuestion(Long questionId);

    /**
     * Интерфейс для метода, который ищет Question по id и изменяет его поле status на 'UPDATED'
     *
     * @param questionId id вопроса, статус которого хотим изменить на 'UPDATED'
     * @return QuestionDto - DTO сущности Question (вопрос обращения)
     */
    Question setStatusUpdated(Long questionId);

    /**
     * Интерфейс для метода, который ищет несколько Question по id и изменяет их поля status на 'UPDATED'
     *
     * @param questionsIds id вопросов, статус которых хотим изменить на 'UPDATED'
     * @return Collection<QuestionDto> - коллекция DTO сущности Question (вопросы обращения)
     */
    Collection<Question> setStatusUpdatedAll(Iterable<Long> questionsIds);

    /**
     * Метод возвращает адрес по Id
     */
    Question findById(Long id);

    /**
     * Метод возвращает адреса по их Id
     */
    Collection<Question> findAllById(Iterable<Long> ids);

    /**
     * Метод возвращает все адреса
     */
    Collection<Question> findAll();

    /**
     * Метод заполняет поле значением и установленной датой
     */
    void moveToArchive(Long id);

    /**
     * Метод ищет объект Question, у которого поле archiveDate имеет значение null
     */
    Question findByIdAndArchivedDateNull(Long id);

    /**
     * Метод ищет сущности Question, у которых поля archiveDate имеют значение null
     */
    Collection<Question> findByAllIdNotArchived(Collection<Long> ids);

    /**
     * Интерфейс для метода вызывающего метод findByAppealId(), который возвращает вопросы относящиеся к нему
     *
     * @param id – id Appeal'a вопросы которого хотим найти
     * @return Collection<Question> - коллекция вопросов Appeal'a
     */
    Collection<Question> findByAppealId(Long id);

}
