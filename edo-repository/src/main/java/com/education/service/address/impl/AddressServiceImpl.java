package com.education.service.address.impl;

import com.education.entity.Address;
import com.education.repository.address.AddressRepository;
import com.education.service.address.AddressService;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import static model.constant.Constant.ADDRESS_URL;



import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

/**
 * Service в "edo-repository", служит для связи контроллера и репозитория
 */
@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    /**
     * Поле "addressRepository" нужно для вызова Repository-слоя (edo-repository),
     * который нужен для связи с БД
     */
    private final AddressRepository addressRepository;

    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    /**
     * Метод сохранения нового адреса в БД
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Address address) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(ADDRESS_URL)
                .setPath("/");
        restTemplate.getForObject(builder.build(), Address.class);
    }

    /**
     * Метод удаления адреса из БД
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Address address) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(ADDRESS_URL)
                .setPath("/")
                .setPath(String.valueOf(address.getId()));
        restTemplate.getForObject(builder.build(), Address.class);
    }

    /**
     * Метод, который возвращает адрес по Id
     */
    @Transactional(readOnly = true)
    @Override
    public Address findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(ADDRESS_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Address.class);
    }


    /**
     * Метод, который возвращает адреса по их Id
     */
    @Transactional(readOnly = true)
    @Override
    public List<Address> findAllById(Iterable<Long> ids) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(ADDRESS_URL)
                .setPath("/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), List.class);
    }

    /**
     * Метод, который возвращает все адреса
     */
    @Transactional(readOnly = true)
    @Override
    public List<Address> findAll() throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(ADDRESS_URL)
                .setPath("/all");
        return restTemplate.getForObject(builder.build(), List.class);
    }
}
