package com.education.service.resolution.impl;

import com.education.entity.FilePool;
import com.education.entity.Resolution;
import com.education.repository.resolution.ResolutionRepository;
import com.education.service.resolution.ResolutionService;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static model.constant.Constant.FILEPOOL_URL;
import static model.constant.Constant.RESOLUTION_URL;


/**
 * Класс содержащий методы для работы и связи с резолюцией
 */
@AllArgsConstructor
@Service
public class ResolutionServiceImpl implements ResolutionService {
    /**
     * Поле для связи с Resolution
     */
    private ResolutionRepository repository;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Resolution resolution) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(RESOLUTION_URL)
                .setPath("/");
        restTemplate.postForObject(builder.build(), resolution, Resolution.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveToArchive(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(RESOLUTION_URL)
                .setPath("/move/")
                .setPath(String.valueOf(id));
        restTemplate.put(builder.build(), Resolution.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Resolution findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(RESOLUTION_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Resolution.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Resolution> findAllById(Collection<Long> id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(RESOLUTION_URL)
                .setPath("/all/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), List.class);
    }


    @Transactional(readOnly = true)
    @Override
    public Resolution findByIdNotArchived(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(RESOLUTION_URL)
                .setPath("/notArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Resolution.class);
    }


    @Transactional(readOnly = true)
    @Override
    public Collection<Resolution> findAllByIdNotArchived(Collection<Long> id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(RESOLUTION_URL)
                .setPath("//notArchived/all/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Collection.class);
    }
}
