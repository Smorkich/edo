package com.education.service.resolution;

import model.dto.ResolutionDto;

import java.net.URISyntaxException;
import java.util.Collection;

/**
 * Интерфейс для связи edo-service c edo-repository
 */
public interface ResolutionService {
    /**
     * Сохраняет новую резолюцию
     */
    public void save(ResolutionDto resolutionDto) throws URISyntaxException;

    /**
     * Архивирует резолюцию
     */
    public void moveToArchive(Long id) throws URISyntaxException;

    /**
     * Поиск резолюции по id
     */
    public ResolutionDto findById(Long id) throws URISyntaxException;

    /**
     * Показать все резолюции
     */
    public Collection<ResolutionDto> findAllById(Long id) throws URISyntaxException;

    /**
     * Поиск исключительно не архивированной резолюции по id
     */
    public ResolutionDto findByIdNotArchived(Long id) throws URISyntaxException;

    /**
     * Показать все актуальные, не помещенные в архив резолюции
     */
    public Collection<ResolutionDto> findAllByIdNotArchived(Long id) throws URISyntaxException;

}
