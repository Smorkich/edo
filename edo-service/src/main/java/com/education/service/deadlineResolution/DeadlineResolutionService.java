package com.education.service.deadlineResolution;

import model.dto.DeadlineResolutionDto;

public interface DeadlineResolutionService {

    /**
     * Получает от DeadlineResolutionListener DeadlineResolutionDto, устанавливает крайний срок для резолюции,
     * с обоснованием причины переноса и передает в Контроллер EDO-Repository для записи в БД
     */
    void reasonForTransferDeadline(DeadlineResolutionDto deadlineResolutionDto);
}
