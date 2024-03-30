package com.education.service.resolution;

import com.education.entity.Resolution;
import model.dto.AppealFileDto;

import java.util.Collection;

/**
 * Интерфейс для работы с ResolutionServiceImpl содержащим реализацию, нужен для возможной подмены реализации не
 * затрагивая код
 */
public interface ResolutionService {
    /**
     * Сохраняет новую резолюцию
     */
    Resolution save(Resolution resolution);

    /**
     * Архивирует резолюцию
     */
    void moveToArchive(Long id);

    /**
     * Разархивирует резолюцию
     */
    void unarchiveResolution(Long resolutionId);

    /**
     * Поиск резолюции по id
     */
    Resolution findById(Long id);

    /**
     * Показать все резолюции
     */
    Collection<Resolution> findAllById(Collection<Long> id);

    /**
     * Поиск всех резолюций которые не черновики (isDraft = false) у конкретного Обращения
     */
    Collection<Resolution> findAllByAppealIdAndIsDraftFalse(Long appealId);

    /**
     * Поиск исключительно не архивированной резолюции по id
     */
    Resolution findByIdNotArchived(Long id);

    /**
     * Показать все актуальные, не помещенные в архив резолюции
     */
    Collection<Resolution> findAllByIdNotArchived(Collection<Long> id);

    /**
     * Receive all needed information about appeal resolutions
     */
    Collection<AppealFileDto> findAllByAppealId(Long appealId);


}
