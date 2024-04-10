package com.education.service.deadlineResolution;

import com.education.entity.DeadlineResolution;
import model.dto.EmailAndIdDto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
     * Получаем список email всех исполнителей
     */
    Collection<EmailAndIdDto> findAllExecutorEmails();

    /**
     * Receive last deadlines associated with resolution collections
     */
    Map<Long, LocalDate> findLastDeadlinesByResolutionsId(List<Long> resolutionId);
}
