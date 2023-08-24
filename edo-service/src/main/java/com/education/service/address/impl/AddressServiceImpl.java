package com.education.service.address.impl;


import com.education.feign.AddressFeignClient;
import com.education.service.address.AddressService;
import lombok.AllArgsConstructor;
import model.dto.AddressDto;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
    private final AddressFeignClient addressFeignClient;


    /**
     * Метод, который возвращает адрес по Id
     */
    public AddressDto findById(long id) {
        return addressFeignClient.findById(id).getBody();
    }

    /**
     * Метод, который возвращает все адреса
     */
    public Collection<AddressDto> findAll() {
        return addressFeignClient.findAll().getBody();
    }

    /**
     * Метод сохранения нового адреса в БД
     */
    public void save(AddressDto addressDto) {
        addressFeignClient.save(addressDto).getBody();
    }

    /**
     * Метод удаления адреса из БД
     */
    public void delete(long id) {
        addressFeignClient.delete(id);
    }

}
