package com.education.service.department.impl;


import com.education.service.department.DepartmentService;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.DepartmentDto;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import static model.constant.Constant.DEPARTMENT_URL;


/**
 * @author Usolkin Dmitry
 * Сервис, который соединяет реализацию двух модулей через RestTemplate
 * Имеет все те же операции, что и service в edo-repository
 */
@Service
@Log4j2
@AllArgsConstructor
public class DepartmentServiceImp implements DepartmentService {
    private static final String URL = "http://edo-repository/api/repository/department";

    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;



    @Override
    public void save(DepartmentDto department) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(DEPARTMENT_URL)
                .setPath("/");
        restTemplate.postForObject(builder.build(), department, DepartmentDto.class);
        log.info("sent a request to save the department in edo - repository");
    }

    @Override
    public void removeToArchived(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(DEPARTMENT_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.delete(builder.build());
        log.info("sent a request to archive the department in edo - repository");
    }

    @Override
    public DepartmentDto findById(Long id) throws URISyntaxException {
        log.info("sent a request to receive the department in edo - repository");
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(DEPARTMENT_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), DepartmentDto.class);
    }

    @Override
    public List<DepartmentDto> findByAllId(String ids) throws URISyntaxException {
        log.info("sent a request to receive the departments in edo - repository");
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(DEPARTMENT_URL)
                .setPath("/all/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), List.class);
    }

    @Override
    public DepartmentDto findByIdNotArchived(Long id) throws URISyntaxException {
        log.info("sent a request to receive the department not archived in edo - repository");
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(DEPARTMENT_URL)
                .setPath("/notArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), DepartmentDto.class);

    }

    @Override
    public List<DepartmentDto> findByAllIdNotArchived(String ids) throws URISyntaxException {
        log.info("sent a request to receive the departments not archived in edo - repository");
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(DEPARTMENT_URL)
                .setPath("/notArchivedAll/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), List.class);
    }


}
