package com.education.service.deadlineResolution;

import com.education.entity.DeadlineResolution;

import java.util.Collection;
import java.util.Optional;

public interface DeadlineResolutionService {

    /**
     * Ищем все переносы крайнего срока, по id резолюции
     */
    Collection<DeadlineResolution> findByResolutionId(Long resolutionId);

    /**
     * Устанавливаем крайний срок для резолюции, с обоснованием переноса
     */
    void saveDeadlineResolution(DeadlineResolution deadlineResolution);

    /**
     * Receive last deadline associated with resolution
     */
    Optional<DeadlineResolution> findLastDeadlineByResolutionId(Long resolutionId);
}
