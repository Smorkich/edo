package com.education.service.resolution;

import com.education.entity.Resolution;

import java.util.Collection;

/**
 * Интерфейс для работы с ResolutionServiceImpl содержащим реализацию, нужен для возможной подмены реализации не
 * затрагивая код
 */
public interface ResolutionService {
    /**
     * Сохраняет новую резолюцию
     */
    public Resolution save(Resolution resolution);

    /**
     * Архивирует резолюцию
     */
    public void moveToArchive(Long id);

    /**
     * Разархивирует резолюцию
     */
    void unarchiveResolution(Long resolutionId);

    /**
     * Поиск резолюции по id
     */
    public Resolution findById(Long id);

    /**
     * Показать все резолюции
     */
    public Collection<Resolution> findAllById(Collection<Long> id);

    /**
     * Поиск всех резолюций которые не черновики (isDraft = false) у конкретного Обращения
     */
    Collection<Resolution> findAllByAppealIdAndIsDraftFalse(Long appealId);

    /**
     * Поиск исключительно не архивированной резолюции по id
     */
    public Resolution findByIdNotArchived(Long id);

    /**
     * Показать все актуальные, не помещенные в архив резолюции
     */
    public Collection<Resolution> findAllByIdNotArchived(Collection<Long> id);


}
