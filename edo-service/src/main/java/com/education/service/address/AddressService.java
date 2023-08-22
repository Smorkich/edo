package com.education.service.address;

import model.dto.AddressDto;

import java.util.Collection;


/**
 * Service в "edo-service", служит для связи контроллера и RestTemplate
 */
public interface AddressService {

    /**
     * Метод, который возвращает адрес по Id
     */

    AddressDto findById(long id);

    /**
     * Метод, который возвращает все адреса
     */
    Collection<AddressDto> findAll();

    /**
     * Метод сохранения нового адреса в БД
     */
    void save(AddressDto addressDto);

    /**
     * Метод удаления адреса из БД
     */
    void delete(long id);

}
