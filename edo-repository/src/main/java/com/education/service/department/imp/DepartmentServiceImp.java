package com.education.service.department.imp;

import com.education.entity.Appeal;
import com.education.entity.Department;
import com.education.repository.department.DepartmentRepository;
import com.education.service.department.DepartmentService;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

import static model.constant.Constant.APPEAL_URL;
import static model.constant.Constant.DEPARTMENT_URL;


/**
 * @author Usolkin Dmitry
 * Сервис, который использует методы Repository
 * Сервис рабоатет с сущностью Department
 * Добавляет, архивирует
 * достает по id департамент,несколько департаментов и департаменты,
 * которые не имеют  дату архивации
 */

@Service
@AllArgsConstructor
public class DepartmentServiceImp implements DepartmentService {
    private final DepartmentRepository repository;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    /**
     * добавляет департамент
     *
     * @param department
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Department department) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(DEPARTMENT_URL)
                .setPath("/");
        restTemplate.postForObject(builder.build(), department, Department.class);
    }

    /**
     * заносит дату архивации, тем самым архивируя департамент
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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
    }

    /**
     * достает департамент по id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Department findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(DEPARTMENT_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Department.class);
    }

    /**
     * достает департамент по нескольким id
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Department> findByAllId(Iterable<Long> ids) throws URISyntaxException {
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

    /**
     * достает департамент без даты архивации по id
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Department findByIdNotArchived(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(DEPARTMENT_URL)
                .setPath("/notArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Department.class);
    }

    /**
     * достает департаменты без даты архивации по нескольким id
     *
     * @param ids
     * @return
     */
    @Transactional(readOnly = true)
    public List<Department> findByAllIdNotArchived(Iterable<Long> ids) throws URISyntaxException {
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
