package com.education.service.address.impl;

import com.education.mapper.GeocodeMapsYandexMapper;
import com.education.service.address.AddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import model.dto.AddressDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;
import static model.constant.Constant.GEOCODE_MAPS_YANDEX_URL;

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
        var builder = buildURI(EDO_REPOSITORY_NAME, ADDRESS_URL).setPath("/").setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), String.class);
    }

    /**
     * Метод, который возвращает объект AddressDto по указанной строке - полному описанию адреса
     * с помощью geocode-maps.yandex
     */
    public AddressDto getAddressDtoByGeocodeMapsYandex(String address) throws JsonProcessingException {
        return new GeocodeMapsYandexMapper().toAddressDto(restTemplate
                .getForObject(GEOCODE_MAPS_YANDEX_URL + address, String.class));
    }

    /**
     * Метод, который возвращает все адреса
     */
    public String findAll() {
        var builder = buildURI(EDO_REPOSITORY_NAME, ADDRESS_URL).setPath("/all");
        return restTemplate.getForObject(builder.toString(), String.class);
    }

    /**
     * Метод сохранения нового адреса в БД
     */
    public void save(AddressDto addressDto) {
        var builder = buildURI(EDO_REPOSITORY_NAME, ADDRESS_URL).setPath("/");
        var uri = (!builder.toString().contains(ADDRESS_URL)) ? builder + ADDRESS_URL : builder.toString();
        restTemplate.postForObject(uri, addressDto, AddressDto.class);
    }

    /**
     * Метод удаления адреса из БД
     */
    public void delete(long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, ADDRESS_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.delete(builder.toString());
    }

}
