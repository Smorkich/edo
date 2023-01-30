package com.education.service.author.impl;

import com.education.entity.Appeal;
import com.education.entity.Author;
import com.education.repository.author.AuthorRepository;
import com.education.service.author.AuthorService;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Random;

import static model.constant.Constant.APPEAL_URL;
import static model.constant.Constant.AUTHOR_URL;

/**
 * Сервис-класс с методами для транзакции с БД
 */

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    /**
     * Сохранение Author
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Author save(Author author) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(AUTHOR_URL)
                .setPath("/");
        return restTemplate.postForObject(builder.build(), author, Author.class);
    }

    /**
     * Удаление Author по id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(readOnly = true)
    public Author findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(AUTHOR_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Author.class);
    }

    /**
     * Поиск Author`s по id`s
     */
//    @Override
//    @Transactional(readOnly = true)
//    public Collection<Author> findAllById(Iterable<Long> ids) {
//        return authorRepository.findAllById(ids);
//    }

    /**
     * Список Author`s
     */

    @Override
    @Transactional(readOnly = true)
    public Collection<Author> findAll() throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(AUTHOR_URL)
                .setPath("/");
        return restTemplate.getForObject(builder.build(), Collection.class);
    }

}
