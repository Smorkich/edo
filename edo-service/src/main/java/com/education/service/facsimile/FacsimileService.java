package com.education.service.facsimile;

import model.dto.EmployeeDto;
import model.dto.FacsimileDto;
import org.springframework.core.io.Resource;

/**
 * Service в "edo-service", служит для связи контроллера и RestTemplate
 */
public interface FacsimileService {

    /**
     * Сохранение
     */
    FacsimileDto save(FacsimileDto facsimileDto);

    /**
     * Получаем Факсимиле через EmployeeId
     */
    FacsimileDto findFacsimileByEmployeeId(Long id);

    Resource getFacsimile(FacsimileDto facsimile);
}
