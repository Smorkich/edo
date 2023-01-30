package com.education.service.employee.impl;

import com.education.entity.Appeal;
import com.education.entity.Employee;
import com.education.repository.employee.EmployeeRepository;
import com.education.service.employee.EmployeeService;
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

import static model.constant.Constant.APPEAL_URL;
import static model.constant.Constant.EMPLOYEE_URL;

/**
 * @author Kiladze George
 * Сервис, который использует методы Repository
 * Сервис рабоатет с сущностью Employee
 * Сохраняет, архивирует
 * предоставляет по id сотрудника, всех сотрудников с указанными id,
 * всех сотрудников или сотрудниковЮ которые не имеют дату архивации
 */
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    /**
     * добавляет сотрудника
     *
     * @param employee
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Employee employee) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/");
        restTemplate.postForObject(builder.build(), employee, Employee.class);
    }

    /**
     * архивация сотрудника с занесением времени архивации
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveToArchived(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        restTemplate.put(builder.build(), Employee.class);
    }

    /**
     * предоставляет сотрудника по id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Employee findById(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Employee.class);
    }

    /**
     * предоставляет всех сотрудников
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> findAll() throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/all");
        return restTemplate.getForObject(builder.build(), Collection.class);
    }

    /**
     * предоставляет сотрудников по некоскольким id
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> findAllById(Iterable<Long> ids) throws URISyntaxException {
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

    /**
     * предоставляет заархивированного сторудника по id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Employee findByIdAndArchivedDateNull(Long id) throws URISyntaxException {
        var instances = eurekaClient.getApplication("edo-repository").getInstances();
        var instance = instances.get(new Random().nextInt(instances.size()));
        var builder = new URIBuilder();
        builder.setHost(instance.getHostName())
                .setPort(instance.getPort())
                .setPath(EMPLOYEE_URL)
                .setPath("/notArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Employee.class);
    }

    /**
     * предоставляет заархивированных сторудников по нескольким id
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> findByIdInAndArchivedDateNull(Iterable<Long> ids) throws URISyntaxException {
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
}
