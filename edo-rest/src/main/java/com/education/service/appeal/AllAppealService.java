package com.education.service.appeal;

import model.dto.AllAppealDto;

public interface AllAppealService {

    /**
     * Метод получения пользователя по id
     */
    AllAppealDto getAllAppeals(Long creatorId, int start, int end);
}
