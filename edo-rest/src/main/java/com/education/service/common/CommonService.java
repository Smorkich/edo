package com.education.service.common;

import java.util.Collection;

public interface CommonService<T> {
    /**
     * Сохраняет сущность в базе данных.
     *
     * @param entityDto Сущность для сохранения.
     */
    T save(T entityDto);

    /**
     * Удаляет сущность из базы данных.
     *
     * @param id Идентификатор сущности.
     */
    void delete(Long id);

    /**
     * Находит сущность по ее идентификатору.
     *
     * @param id Идентификатор сущности.
     * @return Сущность, если она найдена, или null в противном случае.
     */
    T findById(Long id);

    /**
     * Возвращает коллекцию всех сущностей.
     *
     * @return Коллекция всех сущностей.
     */
    Collection<T> findAll();
}
