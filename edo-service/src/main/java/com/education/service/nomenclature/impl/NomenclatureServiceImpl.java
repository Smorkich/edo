package com.education.service.nomenclature.impl;


import com.education.service.nomenclature.NomenclatureService;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.NomenclatureDto;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static model.constant.Constant.NOMENCLATURE_URL;

@AllArgsConstructor
@Service
@Log4j2
public class NomenclatureServiceImpl implements NomenclatureService {

    private final String URL = "http://edo-repository/api/repository/nomenclature";
    private RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    @Override
    public void save(NomenclatureDto nomenclatureDto) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/add");
        restTemplate.postForObject(builder.build(), nomenclatureDto, NomenclatureDto.class);
    }

    @Override
    public NomenclatureDto findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/find/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), NomenclatureDto.class);
    }

    @Override
    public List<NomenclatureDto> findAllById(String ids) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/allId?id=")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), List.class);
    }

    @Override
    public void deleteById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/delete/")
                .setPath(String.valueOf(id));
        restTemplate.delete(builder.build());
    }

    @Override
    public void moveToArchive(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/move/")
                .setPath(String.valueOf(id));
        restTemplate.postForObject(builder.build(),null, NomenclatureDto.class);
    }

    @Override
    public NomenclatureDto findByIdNotArchived(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/find_not_archived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), NomenclatureDto.class);
    }

    @Override
    public List<NomenclatureDto> findAllByIdNotArchived(String ids) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/find_not_archived_List?id=")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), List.class);
    }
}
