package com.education.service.facsimile;

import model.dto.FacsimileDto;
/**
 * Service в "edo-service", служит для связи контроллера и RestTemplate
 */
public interface FacsimileService {

    /**
     * Сохранение
     */
    FacsimileDto save(FacsimileDto facsimileDto);
}
