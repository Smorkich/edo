package com.education.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static model.constant.Constant.*;

/**
 * Базовый интерфейс для Feign клиентов, предоставляющий общую функциональность
 * для работы с сущностями типа T через HTTP API.
 *
 * <p>Этот интерфейс определяет методы для создания, чтения, обновления и удаления
 * сущностей типа T, а также для получения списка всех сущностей. Методы могут быть
 * переопределены в конкретных Feign клиентах для добавления специфической логики
 * или настройки.
 *
 * <p>Используйте аннотацию {@link FeignClient} с атрибутом {@code name} для указания имени сервиса
 * и {@code url} для указания базового URL сервиса, с которым будет взаимодействовать Feign клиент.
 *
 * @param <T> Тип сущности, с которой работает Feign клиент.
 */
@FeignClient(name = EDO_REPOSITORY_NAME, url = BASE_URL)
public interface BaseFeignClient<T> {

    /**
     * Сохраняет сущность типа T в базе данных через HTTP API.
     *
     * @param entity Сущность для сохранения.
     * @return Ответ от сервера, содержащий сохраненную сущность.
     */
    @PostMapping()
    T save(@RequestBody @Valid T entity);

    /**
     * Находит сущность типа T по ее идентификатору через HTTP API.
     *
     * @param id Идентификатор сущности.
     * @return Ответ от сервера, содержащий найденную сущность.
     */
    @GetMapping("/{id}")
    T findById(@PathVariable Long id);

    /**
     * Удаляет сущность типа T по ее идентификатору через HTTP API.
     *
     * @param id Идентификатор сущности для удаления.
     */
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

    /**
     * Возвращает список всех сущностей типа T через HTTP API.
     *
     * @return Ответ от сервера, содержащий список всех сущностей.
     */
    @GetMapping
    Collection<T> findAll();
}
