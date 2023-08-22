package com.education.service.question;

import model.dto.QuestionDto;

import java.util.Collection;

/**
 * @author Nadezhda Pupina
 * Service в "edo-service", создает связь контроллера и RestTemplate
 */
public interface QuestionService {

    /**
     * Метод сохранения нового вопроса в БД
     */
    QuestionDto save(QuestionDto questionDto);

    /**
     * Интерфейс для метода, который сохраняет коллекцию сущностей QuestionDto
     *
     * @param questionDtos - коллекция сохраняемых dto
     * @return Collection<QuestionDto> - коллекция сохранённых dto
     */
    Collection<QuestionDto> saveAll(Collection<QuestionDto> questionDtos);

    /**
     * Метод удаления вопроса из БД
     */
    void delete(long id);

    /**
     * Интерфейс для метода, который отправляет запрос в edo-repository на регистрацию Question
     *
     * @param questionId идентификатор регистрируемого Appeal
     * @return QuestionDto - DTO сущности Question (вопрос обращения)
     */
    QuestionDto registerQuestion(Long questionId);

    /**
     * Интерфейс для метода, который отправляет запрос в edo-repository на регистрацию списка Question
     *
     * @param questionsIds идентификатор регистрируемого Appeal
     * @return QuestionDto - DTO сущности Question (вопрос обращения)
     */
    Collection<QuestionDto> registerAllQuestions(Iterable<Long> questionsIds);

    /**
     * Интерфейс для метода, отправляющего запрос в edo-repository на изменение поля status
     * у Question на 'UPDATED'
     *
     * @param questionId идентификатор изменяемого Question
     * @return QuestionDto - DTO сущности Question (вопрос обращения)
     */
    QuestionDto setStatusUpdated(Long questionId);

    /**
     * Интерфейс для метода, отправляющего запрос в edo-repository на изменение полей status
     * у нескольких Question на 'UPDATED'
     *
     * @param questionsIds идентификаторы изменяемых Question
     * @return QuestionDto - DTO сущности Question (вопрос обращения)
     */
    Collection<QuestionDto> setStatusUpdatedAll(Iterable<Long> questionsIds);

    /**
     * Метод возвращает вопрос по Id
     */
    //String findById(long id);
    QuestionDto findById(long id);

    /**
     * Метод возвращает все вопросы
     */
    Collection<QuestionDto> findAll();

    /**
     * Метод возвращает все вопросы по ids
     */
    Collection<QuestionDto> findAllById(Iterable<Long> ids);

    /**
     * Метод заполняет поле значением и установленной датой
     */
    void moveToArchived(Long id);

    /**
     * Метод ищет объект Question, у которого поле archiveDate имеет значение null
     */
    QuestionDto findByIdNotArchived(Long id);

    /**
     * Метод ищет сущности Question, у которых поля archiveDate имеют значение null
     */
    Collection<QuestionDto> findByAllIdNotArchived(String ids);

    /**
     * <p>Интерфейс для метода, который отправляет запрос в edo-repository на регистрацию Question по id
     *
     * @param id идентификатор регистрируемого Question
     * @return QuestionDto - DTO сущности Question (вопрос обращения)
     */
    Collection<QuestionDto> findByAppealId(Long id);

}
