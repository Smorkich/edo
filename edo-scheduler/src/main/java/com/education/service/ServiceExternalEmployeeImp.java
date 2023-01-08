package com.education.service;


import com.education.job.JobScheduler;
import com.education.mapper.ConvertEmployee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import model.dto.ExternalEmployeeDto;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Usolkin Dmitry
 * Класс планировщик в котором будет метод, который будет нарпавлять обьект EmployeeDto в контроллер Dto каждый час.
 *
 */
@Service
@AllArgsConstructor
@Log4j2
public class ServiceExternalEmployeeImp implements ServiceExternalEmployee {
    private final ObjectMapper objectMapper;
    private final EurekaClient eurekaClient;
    @LoadBalanced
    private final RestTemplate restTemplate;
    private final JobScheduler jobScheduler;
    private final ConvertEmployee convertEmployee;
    private final static String URL = "http://edo-repository/api/repository/employee";

    @Override
    public void save() {
        log.info("The data synchronization method has started, it starts every hour");
        String json = restTemplate.getForObject(jobScheduler.getEmployeeUrl(), String.class);

        List<ExternalEmployeeDto> externalEmployeesDto = null;
        try {
            externalEmployeesDto = objectMapper.readValue(json, new TypeReference<List<ExternalEmployeeDto>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("got an external employee from the andpoint");
        for(ExternalEmployeeDto externalEmployeeDto : externalEmployeesDto) {
            System.out.println(externalEmployeeDto);
            EmployeeDto employeeDto = convertEmployee.toDto(externalEmployeeDto);
            System.out.println(employeeDto);
            Long idSaveEmployee = restTemplate.postForObject(URL,employeeDto, Long.class);
            if (externalEmployeeDto.isDelete()) {
                restTemplate.postForObject(URL + "/" + idSaveEmployee ,null, String.class);
            }
        }
        log.info("converted an external user into That, directed to the save controller and archived an external user whose field IsDeleted = true");

    }

}
