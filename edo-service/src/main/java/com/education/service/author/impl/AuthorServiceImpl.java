package com.education.service.author.impl;

import com.education.service.author.AuthorService;
import com.education.util.URIBuilderUtil;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import model.constant.Constant;
import model.dto.AuthorDto;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static model.constant.Constant.AUTHOR_URL;

/**
 * Сервис-класс с методами для реализации API
 */

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    static final String URL = "http://edo-repository/api/repository/author";
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    /**
     * Сохранение Author
     */
    @Override
    public AuthorDto save(AuthorDto authorDto) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(AUTHOR_URL)
                .setPath("/");
        return restTemplate.postForObject(builder.build(), authorDto, AuthorDto.class);
    }

    /**
     * Удаление Author по id
     */
    @Override
    public void delete(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(AUTHOR_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.delete(builder.build());
    }

    /**
     * Поиск Author по id
     */
    @Override
    public AuthorDto findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(AUTHOR_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), AuthorDto.class);
    }

    /**
     * Список Author`s
     */
    @Override
    public List<AuthorDto> findAll() throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(AUTHOR_URL)
                .setPath("/");
        return restTemplate.getForObject(builder.build(), List.class);
    }
}
