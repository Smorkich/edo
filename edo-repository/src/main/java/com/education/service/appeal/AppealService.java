package com.education.service.appeal;

import com.education.entity.Appeal;

import java.time.ZonedDateTime;
import java.util.Collection;

/**
 * Service в "edo-repository", служит для связи контроллера и репозитория
 */
public interface AppealService {

    /**
     * Сохранение обращения
     */
    void save(Appeal appeal);

    /**
     * Удаление обращения
     */
    void delete(Long id);

    /**
     * Нахождение обращения по id
     */
    Appeal findById(Long id);

    /**
     * Нахождение всех обращений
     */
    Collection<Appeal> findAll();

    /**
     * Перенос в архив обращения
     */
    void moveToArchive(Long id, ZonedDateTime zonedDateTime);

    /**
     * Нахождение обращения по id не из архива
     */
    Appeal findByIdNotArchived(Long id);

    /**
     * Нахождение всех обращений не из архива
     */
    Collection<Appeal> findAllNotArchived();
}