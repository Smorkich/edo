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
     * Поле "restTemplate" нужно для вызова RestTemplate,
     * который нужен для совершения CRUD-операции по заданному URL
     */
    private final RestTemplate restTemplate;

    /**
     * Метод, который возвращает адрес по Id
     */
    public String findById(long id) {
        return restTemplate.getForObject("http://edo-repository/api/repository/address/" + id, String.class);
    }

    /**
     * Метод, который возвращает все адреса
     */
    public String findAll() {
        return restTemplate.getForObject("http://edo-repository/api/repository/address/all", String.class);
    }

    /**
     * Метод сохранения нового адреса в БД
     */
    public void save(AddressDto addressDto) {
        restTemplate.postForObject("http://edo-repository/api/repository/address", addressDto, AddressDto.class);
    }

    /**
     * Метод удаления адреса из БД
     */
    public void delete(long id) {
        restTemplate.delete("http://edo-repository/api/repository/address/" + id, AddressDto.class);
    }

}
