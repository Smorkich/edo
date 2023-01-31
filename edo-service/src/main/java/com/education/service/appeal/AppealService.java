package com.education.service.appeal;

import model.dto.AppealDto;

import java.net.URISyntaxException;
import java.util.Collection;

/**
 * Service в "edo-service", служит для связи контроллера и RestTemplate
 */
public interface AppealService {

    /**
     * Нахождение обращения по id
     */
    AppealDto findById(Long id) throws URISyntaxException;

    /**
     * Нахождение всех обращений
     */
    Collection<AppealDto> findAll();

    /**
     * Сохранение обращения
     */
    AppealDto save(AppealDto appealDto) throws URISyntaxException;

    /**
     * Удаления обращения по Id
     */
    void delete(Long id) throws URISyntaxException;

    /**
     * Перенос обращения в архив по id
     */
    void moveToArchive(Long id) throws URISyntaxException;

    /**
     * Нахождение обращения по id не из архива
     */
    AppealDto findByIdNotArchived(Long id) throws URISyntaxException;

    /**
     * Нахождение всех обращений не из архива
     */
    Collection<AppealDto> findAllNotArchived() throws URISyntaxException;
}
