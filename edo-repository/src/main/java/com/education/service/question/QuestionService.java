package com.education.service.question;

import com.education.entity.Question;

import java.net.URISyntaxException;
import java.util.Collection;

/**
 * @author Nadezhda Pupina
 * Создает связь контроллера и репозитория
 */
public interface QuestionService {
    /**
     * Метод сохранения нового адреса в БД
     */
    Question save(Question question) throws URISyntaxException;

    /**
     * Метод удаления адреса из БД
     */
    void delete(Question question) throws URISyntaxException;

    /**
     * Метод возвращает адрес по Id
     */
    Question findById(Long id) throws URISyntaxException;

    /**
     * Метод возвращает адреса по их Id
     */
//    Collection<Question> findAllById(Iterable<Long> ids);

    /**
     * Метод возвращает все адреса
     */
    Collection<Question> findAll(Iterable<Long> ids) throws URISyntaxException;

    /**
     * Метод заполняет поле значением и установленной датой
     */
    void moveToArchive(Long id) throws URISyntaxException;

    /**
     * Метод ищет объект Question, у которого поле archiveDate имеет значение null
     */
    Question findByIdAndArchivedDateNull(Long id) throws URISyntaxException;

    /**
     * Метод ищет сущности Question, у которых поля archiveDate имеют значение null
     */
    Collection<Question> findByAllIdNotArchived(Collection<Long> ids) throws URISyntaxException;

}
