package com.education.service.appeal;

import model.dto.AppealDto;
import model.dto.AppealFileDto;
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

    void sendMessage(AppealDto appealDto);

    AppealDto findAppealByQuestionsId(Long id);

    AppealDto upload(Long id, FilePoolDto file);

    /**
     * Интерфейс для метода, который добавляет статус REGISTERED обращению и вопросам этого обращения.
     * Отправляет запрос в edo-repository на регистрацию Appeal
     *
     * @param id идентификатор регистрируемого Appeal
     * @return AppealDto - DTO сущности Appeal (обращение)
     */
    AppealDto register(Long id);

    /**
     * Если резолюция становится единственной для обращения и её статус isDraft = false
     * То меняем статус обращения на UNDER_CONSIDERATION
     */
    void setNewAppealStatusIfResolutionLastAndIsDraftFalse(AppealDto appealDto);

    /**
     * Check that the appeal associated with the resolution with resolutionId has all resolutions completed
     * and if that right, change the appeal status depending on the registrationDate field
     * @param resolutionId - id of the archived resolution
     */
    void setAppealStatusIfLastResolutionArchived(Long resolutionId);

    /**
     * Receive all needed information about appeal resolutions
     */
    Collection<AppealFileDto> findAllByAppealIdForXLSX(Long appealId);
}
