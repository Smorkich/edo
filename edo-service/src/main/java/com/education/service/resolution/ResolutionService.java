package com.education.service.resolution;

import model.dto.ResolutionDto;

import java.util.Collection;

/**
 * Интерфейс для связи edo-service c edo-repository
 */
public interface ResolutionService {
    /**
     * Сохраняет новую резолюцию
     */
    ResolutionDto save(ResolutionDto resolutionDto);

    /**
     * Разархивации резолюции
     */
    void unarchiveResolution(Long resolutionId);

    /**
     * Архивирует резолюцию
     */
    void moveToArchive(Long id);

    /**
     * Поиск резолюции по id
     */
    ResolutionDto findById(Long id);

    /**
     * Показать все резолюции
     */
    Collection<ResolutionDto> findAllById(Long id);

    /**
     * Поиск исключительно не архивированной резолюции по id
     */
    ResolutionDto findByIdNotArchived(Long id);

    /**
     * Показать все актуальные, не помещенные в архив резолюции
     */
    Collection<ResolutionDto> findAllByIdNotArchived(Long id);

}
