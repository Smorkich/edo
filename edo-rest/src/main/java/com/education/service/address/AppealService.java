package com.education.service.address;

import model.dto.AppealDto;

/**
 * Service в "edo-rest", служит для связи контроллера и RestTemplate
 */
public interface AppealService {

    /**
     * Метод сохранения нового адреса в БД
     */
    AppealDto save(AppealDto appealDto);


}
