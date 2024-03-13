package com.education.service.common.impl;

import com.education.service.common.CommonService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static model.constant.Constant.EDO_SERVICE_NAME;

/**
 * Абстрактный сервис, предоставляющий базовую функциональность для работы с сущностями типа T
 * через RestTemplate.
 *
 * <p>Этот класс реализует интерфейс {@link CommonService}, предоставляя методы для сохранения,
 * поиска, удаления и получения всех сущностей типа T. Реализация этих методов использует
 * {@link RestTemplate} для взаимодействия с удаленным сервисом через HTTP API.
 *
 * @param <T> Тип сущности, с которой работает сервис.
 */
@Service
@AllArgsConstructor
public abstract class AbstractService<T> implements CommonService<T> {

    private final RestTemplate restTemplate;

    /**
     * Сохраняет сущность типа T в базе данных через RestTemplate.
     *
     * @param entityDto Сущность для сохранения.
     * @return Сохраненная сущность.
     */
    public T save(T entityDto) {
        String uri = URIBuilderUtil.buildURI(EDO_SERVICE_NAME, getSavePath()).toString();
        HttpHeaders headers = createHeaders();
        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(entityDto, headers), getEntityClass())
                .getBody();
    }

    /**
     * Удаляет сущность типа T по ее идентификатору через RestTemplate.
     *
     * @param id Идентификатор сущности для удаления.
     */
    public void delete(Long id) {
        String uri = URIBuilderUtil.buildURI(EDO_SERVICE_NAME, getDeletePath(id)).toString();
        restTemplate.delete(uri);
    }

    /**
     * Находит сущность типа T по ее идентификатору через RestTemplate.
     *
     * @param id Идентификатор сущности.
     * @return Найденная сущность.
     */
    public T findById(Long id) {
        String uri = URIBuilderUtil.buildURI(EDO_SERVICE_NAME, getFindByIdPath(id)).toString();
        return restTemplate.getForObject(uri, getEntityClass());
    }

    /**
     * Возвращает список всех сущностей типа T через RestTemplate.
     *
     * @return Список всех сущностей.
     */
    public Collection<T> findAll() {
        String uri = URIBuilderUtil.buildURI(EDO_SERVICE_NAME, getFindAllPath()).toString();
        return restTemplate.getForObject(uri, Collection.class);
    }

    /**
     * Создает HTTP заголовки для запросов.
     *
     * @return HTTP заголовки.
     */
    protected HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Методы, которые должны быть переопределены в конкретных сервисах
     *
     * <p><b>Абстрактные методы:</b>
     * <p>
     * Возвращает класс сущности типа T.
     *
     * @return Класс сущности.
     */
    protected abstract Class<T> getEntityClass();

    /**
     * Возвращает путь для сохранения сущности типа T.
     *
     * @return Путь для сохранения.
     */
    protected abstract String getSavePath();

    /**
     * Возвращает путь для поиска сущности типа T по идентификатору.
     *
     * @param id Идентификатор сущности.
     * @return Путь для поиска.
     */
    protected abstract String getFindByIdPath(Long id);

    /**
     * Возвращает путь для получения всех сущностей типа T.
     *
     * @return Путь для получения всех сущностей.
     */
    protected abstract String getFindAllPath();

    /**
     * Возвращает путь для удаления сущности типа T по идентификатору.
     *
     * @param id Идентификатор сущности.
     * @return Путь для удаления.
     */
    protected abstract String getDeletePath(Long id);

}
