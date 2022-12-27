package com.education.service.address;

import model.dto.AppealDto;
import model.dto.AuthorDto;

import java.util.Collection;

/**
 * Service в "edo-service", служит для связи контроллера и RestTemplate
 */
public interface AppealService {

    /**
     * Метод, который возвращает адрес по Id
     */
    String findById(long id);

    /**
     * Метод, который возвращает все адреса
     */


    /**
     * Метод сохранения нового адреса в БД
     */
    void save(AppealDto appealDto);

    /**
     * Метод удаления адреса из БД
     */
    void delete(long id);

    Collection<AuthorDto> findAll();

}
