package com.education.service.common;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import service.CommonService;

import java.util.Collection;

/**
 * Абстрактный сервис, предоставляющий базовую функциональность для работы с сущностями типа T
 * через RestTemplate.
 *
 * <p>Этот класс реализует интерфейс {@link CommonService}, предоставляя методы для сохранения,
 * поиска, удаления и получения всех сущностей типа T. Реализация этих методов использует
 * {@link BaseRestFeignClient} для взаимодействия с удаленным сервисом через HTTP API.
 *
 * @param <T> Тип сущности, с которой работает сервис.
 */
@Service
@NoArgsConstructor(force = true)
public abstract class AbstractRestService<T> implements CommonService<T> {

    private final BaseRestFeignClient<T> feignClient;

    /**
     * Сохраняет сущность типа T в базе данных через Feign клиент.
     *
     * @param entity Сущность для сохранения.
     * @return Сохраненная сущность.
     * @throws IllegalStateException Если Feign клиент не инициализирован.
     */
    @Override
    public T save(T entity) {
        if (feignClient == null) {
            throw new IllegalStateException("Repository is not initialized");
        }
        return feignClient.save(entity);
    }

    /**
     * Находит сущность типа T по ее идентификатору через Feign клиент.
     *
     * @param id Идентификатор сущности.
     * @return Найденная сущность.
     * @throws IllegalStateException Если Feign клиент не инициализирован.
     */
    @Override
    public T findById(Long id) {
        if (feignClient == null) {
            throw new IllegalStateException("Repository is not initialized");
        }
        return feignClient.findById(id);
    }

    /**
     * Удаляет сущность типа T по ее идентификатору через Feign клиент.
     *
     * @param id Идентификатор сущности для удаления.
     * @throws IllegalStateException Если Feign клиент не инициализирован.
     */
    @Override
    public void delete(Long id) {
        if (feignClient == null) {
            throw new IllegalStateException("Repository is not initialized");
        }
        feignClient.delete(id);
    }

    /**
     * Возвращает список всех сущностей типа T через Feign клиент.
     *
     * @return Список всех сущностей.
     * @throws IllegalStateException Если Feign клиент не инициализирован.
     */
    @Override
    public Collection<T> findAll() {
        if (feignClient == null) {
            throw new IllegalStateException("Repository is not initialized");
        }
        return feignClient.findAll();
    }
}
