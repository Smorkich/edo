package com.education.service.common;

import com.education.entity.BaseEntity;
import com.education.repository.common.CommonRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Абстрактный сервис, предоставляющий базовую функциональность для работы с сущностями,
 * расширяющими {@link BaseEntity}, и репозиториями, реализующими {@link CommonRepository}.
 *
 * @param <E> Тип сущности, с которой работает сервис. Должен расширять {@link BaseEntity}.
 * @param <R> Тип репозитория, используемого сервисом. Должен расширять {@link CommonRepository}.
 */
@Service
@NoArgsConstructor(force = true)
public abstract class AbstractService<E extends BaseEntity, R extends CommonRepository<E>> implements CommonService<E> {

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
        Optional<E> entity = repository.findById(id);
        return entity.orElse(null);
    }

    /**
     * Возвращает список всех сущностей.
     *
     * @return Список всех сущностей.
     */
    @Transactional(readOnly = true)
    public List<E> findAll() {
        return repository.findAll();
    }

    /**
     * Сохраняет сущность в базе данных.
     *
     * @param entity Сущность для сохранения.
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(E entity) {
        repository.save(entity);
    }

    /**
     * Удаляет сущность из базы данных.
     *
     * @param entity Сущность для удаления.
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(E entity) {
        repository.delete(entity);
    }
}
