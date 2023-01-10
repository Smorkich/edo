package com.education.service;


import com.education.job.JobScheduler;
import com.education.mapper.ConvertEmployee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import model.dto.ExternalEmployeeDto;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

/**
 * @author Usolkin Dmitry
 * Класс планировщик в котором будет метод, который будет нарпавлять обьект EmployeeDto в контроллер Dto каждый час.
 */
@Component
@AllArgsConstructor
@Log4j2
public class ServiceExternalEmployeeImp implements ServiceExternalEmployee {
    private final ObjectMapper objectMapper;
    private final EurekaClient eurekaClient;
    @LoadBalanced
    private final RestTemplate restTemplate;
    private final JobScheduler jobScheduler;
    private final ConvertEmployee convertEmployee;


    @Override
    @Scheduled(cron = "${cron.employee}")
    public void save() {
        log.info("The data synchronization method has started, it starts every hour");
        String json = restTemplate.getForObject(jobScheduler.getEmployeeUrl(), String.class);
        log.info("Received JSON from the client");
        int random = new Random().nextInt(eurekaClient.getApplication("edo-repository").getInstances().size());
        log.info("Created a random number , for instances");
        System.out.println(random);
        String host = eurekaClient.getApplication("edo-repository").getInstances().get(random).getHostName();

        URIBuilder uriBuilder = new URIBuilder()
                .setScheme("http")
                .setHost(host)
                .setPort(eurekaClient.getApplication("edo-repository").getInstances().get(random).getPort())
                .setPath("/api/repository/employee");
        String urlEmployee = uriBuilder.toString();
        URIBuilder uriBuilderTwo = new URIBuilder()
                .setScheme("http")
                .setHost(host)
                .setPort(eurekaClient.getApplication("edo-repository").getInstances().get(random).getPort())
                .setPath("/api/repository/department");
        String urlDepartment = uriBuilderTwo.toString();
        log.info("Using Eureka Client, we created a URL for requests via RestTemplate");
        List<ExternalEmployeeDto> externalEmployeesDto = null;
        try {
            externalEmployeesDto = objectMapper.readValue(json, new TypeReference<List<ExternalEmployeeDto>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("Converted JSON using ObjectMapper into an object ExternalEmployeeDto ");
        for (ExternalEmployeeDto externalEmployeeDto : externalEmployeesDto) {
            EmployeeDto employeeDto = convertEmployee.toDto(externalEmployeeDto);
            EmployeeDto employeeDto1 = restTemplate.postForObject(urlEmployee, employeeDto, EmployeeDto.class);
            if (externalEmployeeDto.isDelete()) {
                restTemplate.postForObject(urlEmployee + "/" + employeeDto1.getId(), null, String.class);
            }
            if (externalEmployeeDto.getCompany().isDelete()) {
                restTemplate.postForObject(urlDepartment + "/" + employeeDto1.getDepartment().getId(), null, String.class);
            }
        }
        log.info("converted an external user into That, directed to the save controller and archived an external user whose field IsDeleted = true");

    }

}
