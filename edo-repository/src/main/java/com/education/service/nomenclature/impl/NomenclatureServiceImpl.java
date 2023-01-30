package com.education.service.nomenclature.impl;

import com.education.entity.Appeal;
import com.education.entity.Nomenclature;
import com.education.repository.nomenclature.NomenclatureRepository;
import com.education.service.nomenclature.NomenclatureService;
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
import java.util.Optional;
import java.util.Random;

import static model.constant.Constant.APPEAL_URL;
import static model.constant.Constant.NOMENCLATURE_URL;


/** Implementation of edo-service service */
@AllArgsConstructor
@Service
public class NomenclatureServiceImpl implements NomenclatureService {

    private NomenclatureRepository repository;
     private final RestTemplate restTemplate;
     private final EurekaClient eurekaClient;

    /**
     * Method saves new entity in DB by accepting json-body object
     */
    @Transactional(rollbackFor = Exception.class)
    public Nomenclature save(Nomenclature nomenclature) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/add");
        return restTemplate.postForObject(builder.build(), nomenclature, Nomenclature.class);
    }

    /**
     * the Method fills in the field with the value and set date
     */
    @Transactional(rollbackFor = Exception.class)
    public void moveToArchive(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/move/")
                .setPath(String.valueOf(id));
        restTemplate.put(builder.build(), Nomenclature.class);
    }

    /**
     * Method searches for an entity of Nomenclature
     */
    public Nomenclature findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/find/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Nomenclature.class);
    }

    /**
     * Method searches for set of entities of Nomenclature by their ids: "?id = 1,2,3,4,5,6... "
     */
    public List<Nomenclature> findAllById(Collection<Long> nomenclature) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/all");
        return restTemplate.getForObject(builder.build(), List.class);
    }
    
    /**
     * Method searches for an entity of Nomenclature that archiveDate fild is null
     */
    @Override
    public Optional<Nomenclature> findByIdNotArchived(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/notArch/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Optional.class);
    }

    /**
     * Method searches for set of entities of Nomenclature that archiveDate filds are null
     */
    @Override
    public List<Nomenclature> findAllByIdNotArchived(Collection<Long> idList) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(NOMENCLATURE_URL)
                .setPath("/notArchList")
                .setPath(String.valueOf(idList));
        return restTemplate.getForObject(builder.build(), List.class);
    }

    /**
     * Delete entity of Nomenclature by its id
     */
    @Transactional(rollbackFor = Exception.class)
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
}
