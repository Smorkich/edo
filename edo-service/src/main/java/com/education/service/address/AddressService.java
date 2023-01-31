package com.education.service.address;

import model.dto.AddressDto;

import java.net.URISyntaxException;

/**
 * Service в "edo-service", служит для связи контроллера и RestTemplate
 */
public interface AddressService {

    /**
     * Метод, который возвращает адрес по Id
     */
    String findById(long id) throws URISyntaxException;

    /**
     * Метод, который возвращает все адреса
     */
    String findAll() throws URISyntaxException;

    /**
     * Метод сохранения нового адреса в БД
     */
    void save(AddressDto addressDto) throws URISyntaxException;

    /**
     * Метод удаления адреса из БД
     */
    void delete(long id) throws URISyntaxException;

}
