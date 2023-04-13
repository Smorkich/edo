package com.education.service.deadlineResolution;

import com.education.entity.DeadlineResolution;

import java.util.Collection;

public interface DeadlineResolutionService {

    /**
     * Ищем все переносы крайнего срока, по id резолюции
     */
    Collection<DeadlineResolution> findByResolutionId(Long resolutionId);

    /**
     * Устанавливаем крайний срок для резолюции, с обоснованием переноса
     */
    void saveDeadlineResolution(DeadlineResolution deadlineResolution);
}
