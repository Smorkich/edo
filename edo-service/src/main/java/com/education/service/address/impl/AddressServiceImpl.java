package com.education.service.address.impl;

import com.education.service.address.AddressService;
import lombok.AllArgsConstructor;
import model.dto.AddressDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service в "edo-service", служит для связи контроллера и RestTemplate
 */
@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    /**
     * Константа "URL" хранит URL по которому мы делаем запрос при помощи RestTemplate
     */
    private final String URL = "http://edo-repository/api/repository/address";

    /**
     * Поле "restTemplate" нужно для вызова RestTemplate,
     * который нужен для совершения CRUD-операции по заданному URL
     */
    private final RestTemplate restTemplate;

    /**
     * Метод, который возвращает адрес по Id
     */
    public String findById(long id) {
        return restTemplate.getForObject(URL + "/" + id, String.class);
    }

    /**
     * Метод, который возвращает все адреса
     */
    public String findAll() {
        return restTemplate.getForObject(URL + "/all", String.class);
    }

    /**
     * Метод сохранения нового адреса в БД
     */
    public void save(AddressDto addressDto) {
        restTemplate.postForObject(URL, addressDto, AddressDto.class);
    }

    /**
     * Метод удаления адреса из БД
     */
    public void delete(long id) {
        restTemplate.delete(URL + "/" + id, AddressDto.class);
    }

}
