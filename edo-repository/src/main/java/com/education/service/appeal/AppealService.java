package com.education.service.appeal;

import com.education.entity.Appeal;

import java.util.Collection;

/**
 * Service в "edo-repository", служит для связи контроллера и репозитория
 */
public interface AppealService {

    /**
     * Сохранение обращения
     */
    Appeal save(Appeal appeal);

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
    void moveToArchive(Long id);

    /**
     * Нахождение обращения по id не из архива
     */
    Appeal findByIdNotArchived(Long id);

    /**
     * Нахождение всех обращений не из архива
     */
    Collection<Appeal> findAllNotArchived();

    /**
     * Нахождение обращения по Questions id
     */
    Appeal findAppealByQuestionsId(Long id);

    /**
     * Интерфейс для метода, который изменяет статус строки Appeal в базе данных на REGISTERED
     * @param id идентификатор регистрируемого Appeal
     * @return AppealDto - DTO сущности Appeal (обращение)
     */
    Appeal register(Long id);

    /**
     * Check that the appeal associated with the resolution with resolutionId has all resolutions completed and
     * change the appeal status depending on the registrationDate field
     * @param resolutionId - id of the archived resolution
     */
    void setAppealStatusIfLastResolutionArchived(Long resolutionId);

}