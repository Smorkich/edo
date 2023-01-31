package com.education.service.address.impl;

import com.education.service.address.AddressService;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import model.dto.AddressDto;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import static model.constant.Constant.ADDRESS_URL;

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

    private final EurekaClient eurekaClient;

    /**
     * Метод, который возвращает адрес по Id
     */
    public String findById(long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-service").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(ADDRESS_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), String.class);
    }

    /**
     * Метод, который возвращает все адреса
     */
    public String findAll() throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-service").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(ADDRESS_URL)
                .setPath("/all");
        return restTemplate.getForObject(builder.build(), String.class);
    }

    /**
     * Метод сохранения нового адреса в БД
     */
    public void save(AddressDto addressDto) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-service").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(ADDRESS_URL)
                .setPath("/");
        restTemplate.postForObject(builder.build(), addressDto, AddressDto.class);
    }

    /**
     * Метод удаления адреса из БД
     */
    public void delete(long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-service").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(ADDRESS_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.delete(builder.build());
    }

}
