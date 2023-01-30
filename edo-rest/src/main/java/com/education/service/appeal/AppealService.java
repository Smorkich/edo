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

    void moveToArchive(Long id);

    AppealDto findById(Long id);
}
