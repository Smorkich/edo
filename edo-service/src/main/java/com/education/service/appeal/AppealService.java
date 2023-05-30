package com.education.service.appeal;

import model.dto.AppealDto;
import model.dto.FilePoolDto;

import java.util.Collection;

/**
 * Service в "edo-service", служит для связи контроллера и RestTemplate
 */
public interface AppealService {

    /**
     * Нахождение обращения по id
     */
    AppealDto findById(Long id);

    /**
     * Нахождение всех обращений
     */
    Collection<AppealDto> findAll();

    /**
     * Сохранение обращения
     */
    AppealDto save(AppealDto appealDto);

    /**
     * Удаления обращения по Id
     */
    void delete(Long id);

    /**
     * Перенос обращения в архив по id
     */
    void moveToArchive(Long id);

    /**
     * Нахождение обращения по id не из архива
     */
    AppealDto findByIdNotArchived(Long id);

    /**
     * Нахождение всех обращений не из архива
     */
    Collection<AppealDto> findAllNotArchived();

    public void sendMessage(AppealDto appealDto);

    AppealDto findAppealByQuestionsId(Long id);

    AppealDto upload(Long id, FilePoolDto file);
}
