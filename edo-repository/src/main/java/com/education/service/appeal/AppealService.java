package com.education.service.appeal;

import com.education.entity.Appeal;

import java.net.URISyntaxException;
import java.util.Collection;

/**
 * Service в "edo-repository", служит для связи контроллера и репозитория
 */
public interface AppealService {

    /**
     * Сохранение обращения
     */
    Appeal save(Appeal appeal) throws URISyntaxException;

    /**
     * Удаление обращения
     */
    void delete(Long id) throws URISyntaxException;

    /**
     * Нахождение обращения по id
     */
    Appeal findById(Long id) throws URISyntaxException;

    /**
     * Нахождение всех обращений
     */
    Collection<Appeal> findAll() throws URISyntaxException;

    /**
     * Перенос в архив обращения
     */
    void moveToArchive(Long id) throws URISyntaxException;

    /**
     * Нахождение обращения по id не из архива
     */
    Appeal findByIdNotArchived(Long id) throws URISyntaxException;

    /**
     * Нахождение всех обращений не из архива
     */
    Collection<Appeal> findAllNotArchived() throws URISyntaxException;
}