package com.education.service.appeal;

import model.dto.AllAppealDto;

public interface AllAppealService {

    /**
     * Метод получения пользователя по id
     */
    AllAppealDto getAllAppeals(int lastUser, int numberOfUsersToDisplay);
}
