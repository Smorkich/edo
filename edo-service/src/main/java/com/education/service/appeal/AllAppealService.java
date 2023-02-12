package com.education.service.appeal;

import model.dto.AllAppealDto;


/**
 * Service в "edo-service", служит для связи контроллера и RestTemplate
 */
public interface AllAppealService {

    /**
     * Получение всех обращений
     */
    AllAppealDto getAllAppeals(int lastUser, int numberOfUsersToDisplay);

}
