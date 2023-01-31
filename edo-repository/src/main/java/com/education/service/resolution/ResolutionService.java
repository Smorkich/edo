package com.education.service.resolution;

import com.education.entity.Resolution;
import org.apache.catalina.LifecycleState;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Интерфейс для работы с ResolutionServiceImpl содержащим реализацию, нужен для возможной подмены реализации не
 * затрагивая код
 */
public interface ResolutionService {
    /**
     * Сохраняет новую резолюцию
     */
    public void save(Resolution resolution);

    /**
     * Архивирует резолюцию
     */
    public void moveToArchive(Long id);

    /**
     * Поиск резолюции по id
     */
    public Resolution findById(Long id);

    /**
     * Показать все резолюции
     */
    public Collection<Resolution> findAllById(Collection<Long> id);

    /**
     * Поиск исключительно не архивированной резолюции по id
     */
    public Resolution findByIdNotArchived(Long id);

    /**
     * Показать все актуальные, не помещенные в архив резолюции
     */
    public Collection<Resolution> findAllByIdNotArchived(Collection<Long> id);


}
