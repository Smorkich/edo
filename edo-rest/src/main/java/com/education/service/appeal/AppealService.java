package com.education.service.appeal;

import model.dto.AppealDto;

/**
 * Service в "edo-rest", служит для связи контроллера и RestTemplate
 */
public interface AppealService {

    /**
     * Метод сохранения нового обращения в БД
     */
    AppealDto save(AppealDto appealDto);

    /**
     * Метод отправки сообщения в архив
     */
    void moveToArchive(Long id);

    /**
     * Метод получения пользователя по id
     */
    AppealDto findById(Long id);
}
