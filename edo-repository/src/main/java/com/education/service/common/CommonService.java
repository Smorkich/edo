package com.education.service.common;

import com.education.entity.BaseEntity;

import java.util.Collection;

/**
 * Интерфейс, определяющий базовые операции с сущностями, расширяющими {@link BaseEntity}.
 *
 * @param <E> Тип сущности, с которой работает сервис. Должен расширять {@link BaseEntity}.
 */
public interface CommonService<E extends BaseEntity> {

    /**
     * Сохраняет сущность в базе данных.
     *
     * @param entity Сущность для сохранения.
     */
    void save(E entity);

    /**
     * Удаляет сущность из базы данных.
     *
     * @param entity Сущность для удаления.
     */
    void delete(E entity);

    /**
     * Находит сущность по ее идентификатору.
     *
     * @param id Идентификатор сущности.
     * @return Сущность, если она найдена, или null в противном случае.
     */
    E findById(Long id);

    /**
     * Возвращает коллекцию всех сущностей.
     *
     * @return Коллекция всех сущностей.
     */
    Collection<E> findAll();
}
