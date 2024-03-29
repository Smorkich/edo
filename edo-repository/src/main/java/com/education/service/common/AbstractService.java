package com.education.service.common;

import com.education.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Абстрактный сервис, предоставляющий базовую функциональность для работы с сущностями,
 * расширяющими {@link BaseEntity}, и репозиториями, реализующими {@link JpaRepository}.
 *
 * @param <E> Тип сущности, с которой работает сервис. Должен расширять {@link BaseEntity}.
 * @param <R> Тип репозитория, используемого сервисом. Должен расширять {@link JpaRepository}.
 */
@Service
@NoArgsConstructor(force = true)
@AllArgsConstructor
public abstract class AbstractService<E extends BaseEntity, R extends JpaRepository<E, Long>> implements CommonService<E> {

    /**
     * Репозиторий, используемый сервисом для взаимодействия с базой данных.
     */
    protected final R repository;

    /**
     * Находит сущность по ее идентификатору.
     *
     * @param id Идентификатор сущности.
     * @return Сущность, если она найдена, или null в противном случае.
     */
    @Transactional(readOnly = true)
    public E findById(Long id) {
        if (repository == null) {
            throw new IllegalStateException("Repository is not initialized");
        }
        return repository.findById(id).orElse(null);
    }

    /**
     * Возвращает список всех сущностей.
     *
     * @return Список всех сущностей.
     */
    @Transactional(readOnly = true)
    public List<E> findAll() {
        if (repository == null) {
            throw new IllegalStateException("Repository is not initialized");
        }
        return repository.findAll();
    }

    /**
     * Возвращает коллекцию всех сущностей.
     *
     * @return Коллекция всех сущностей.
     */
    @Transactional(readOnly = true)
    public List<E> findAllById(Iterable<Long> ids) {
        if (repository == null) {
            throw new IllegalStateException("Repository is not initialized");
        }
        return repository.findAllById(ids);
    }

    /**
     * Сохраняет сущность в базе данных.
     *
     * @param entity Сущность для сохранения.
     */
    @Transactional(rollbackFor = Exception.class)
    public E save(E entity) {
        if (repository == null) {
            throw new IllegalStateException("Repository is not initialized");
        }
        return repository.saveAndFlush(entity);
    }

    /**
     * Удаляет сущность из базы данных.
     *
     * @param id Идентификатор сущности.
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (repository == null) {
            throw new IllegalStateException("Repository is not initialized");
        }
        repository.deleteById(id);
    }

    /**
     * Удаляет сущность из базы данных.
     *
     * @param entity Сущность для удаления.
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(E entity) {
        if (repository == null) {
            throw new IllegalStateException("Repository is not initialized");
        }
        repository.delete(entity);
    }
}
