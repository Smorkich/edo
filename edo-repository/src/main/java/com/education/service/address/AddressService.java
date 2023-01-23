package com.education.service.address;

import com.education.entity.Address;

import java.net.URISyntaxException;
import java.util.Collection;

/**
 * Service в "edo-repository", служит для связи контроллера и репозитория
 */
public interface AddressService {

    /**
     * Метод сохранения нового адреса в БД
     */
    void save(Address address) throws URISyntaxException;

    /**
     * Метод удаления адреса из БД
     */
    void delete(Address address) throws URISyntaxException;

    /**
     * Метод, который возвращает адрес по Id
     */
    Address findById(Long id) throws URISyntaxException;

    /**
     * Метод, который возвращает адреса по их Id
     */
    Collection<Address> findAllById(Iterable<Long> ids) throws URISyntaxException;

    /**
     * Метод, который возвращает все адреса
     */
    Collection<Address> findAll() throws URISyntaxException;

}
