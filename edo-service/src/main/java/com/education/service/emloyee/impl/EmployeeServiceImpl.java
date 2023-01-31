package com.education.service.emloyee.impl;

import com.education.service.emloyee.EmployeeService;
import com.education.util.URIBuilderUtil;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static model.constant.Constant.*;

/**
 * @author Kiladze George & Kryukov Andrey
 * Сервис, который соединяет реализацию двух модулей через RestTemplate
 * Имеет все те же операции, что и service в edo-repository
 */
@Service
@Log4j2
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    static final String URL = "http://edo-repository/api/repository/employee";
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    @Override
    public void save(EmployeeDto employeeDto) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/");
        restTemplate.postForObject(builder.build(), employeeDto, EmployeeDto.class);
        log.info("Sent a request to save the employee in edo - repository");
    }

    @Override
    public void moveToArchived(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.postForObject(builder.build(),null, String.class);
        log.info("Sent a request to archive the employee in edo - repository");
    }

    @Override
    public EmployeeDto findById(Long id) throws URISyntaxException {
        log.info("Sent a request to receive the employee in edo - repository");
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), EmployeeDto.class);
    }

    @Override
    public Collection<EmployeeDto> findAll() throws URISyntaxException {
        log.info("Sent a request to receive all employee in edo - repository");
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/all");
        return restTemplate.getForObject(builder.build(), Collection.class);
    }

    @Override
    public Collection<EmployeeDto> findAllById(String ids) throws URISyntaxException {
        log.info("Sent a request to receive the employee in edo - repository");
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/all/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), Collection.class);
    }

    @Override
    public EmployeeDto findByIdAndArchivedDateNull(Long id) throws URISyntaxException {
        log.info("Sent a request to receive the employee archived in edo - repository");
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/notArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), EmployeeDto.class);
    }

    @Override
    public Collection<EmployeeDto> findByIdInAndArchivedDateNull(String ids) throws URISyntaxException {
        log.info("Sent a request to receive the employee not archived in edo - repository");
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/notArchivedAll/")
                .setPath(String.valueOf(ids));
        return restTemplate.getForObject(builder.build(), List.class);
    }

    @Override
    public Collection<EmployeeDto> findAllByFullName(String fullName) throws URISyntaxException {
        log.info("Build uri to repository");
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/api/repository/employee/search")
                .addParameter(EMPLOYEE_FIO_SEARCH_PARAMETER, fullName).toString();
        log.info("Sent a request to receive the employee collection with requested full name");
        return restTemplate.getForObject(builder.build(), Collection.class);
    }
}
