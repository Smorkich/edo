package com.education.service.appeal.impl;

import com.education.entity.Address;
import com.education.entity.Appeal;
import com.education.repository.appeal.AppealRepository;
import com.education.service.appeal.AppealService;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Random;

import static model.constant.Constant.ADDRESS_URL;
import static model.constant.Constant.APPEAL_URL;

/**
 * Сервис-слой для сущности Appeal
 */
@Service
@AllArgsConstructor
public class AppealServiceImpl implements AppealService {

    private AppealRepository appealRepository;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    /**
     * Сохранение обращения
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Appeal save(Appeal appeal) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(APPEAL_URL)
                .setPath("/");
        return restTemplate.getForObject(builder.build(), Appeal.class);
    }

    /**
     * Удаление обращения
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(APPEAL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.delete(builder.build());
    }

    /**
     * Нахождение обращения по id
     */
    @Override
    @Transactional(readOnly = true)
    public Appeal findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(APPEAL_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Appeal.class);
    }

    /**
     * Нахождение всех обращений
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Appeal> findAll() throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(APPEAL_URL)
                .setPath("/findAllNotArchived");
        return restTemplate.getForObject(builder.build(), Collection.class);
    }

    /**
     * Перенос в архив обращения
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveToArchive(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(APPEAL_URL)
                .setPath("/move/")
                .setPath(String.valueOf(id));
        // Appeal или создать объект
         restTemplate.put(builder.build(), Appeal.class);
    }

    /**
     * Нахождение обращения по id не из архива
     */
    @Override
    @Transactional(readOnly = true)
    public Appeal findByIdNotArchived(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(APPEAL_URL)
                .setPath("/findByIdNotArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Appeal.class);
    }

    /**
     * Нахождение всех обращений не из архива
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Appeal> findAllNotArchived() throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(APPEAL_URL)
                .setPath("/findByIdNotArchived");
        return restTemplate.getForObject(builder.build(), Collection.class);
    }
}