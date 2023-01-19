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
import model.util.UriBuilderUtil;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;


import static model.constant.ConstantScheduler.EDO_REPOSITORY_NAME;
import static model.constant.ConstantScheduler.URL_DEPARTMENT_PATH;
import static model.constant.ConstantScheduler.URL_EMPLOYEE_PATH;
import static org.apache.http.HttpVersion.HTTP;

/**
 * @author Usolkin Dmitry & Kostenko Aleksandr
 * Класс планировщик с методом, который будет нарпавлять обьект EmployeeDto в контроллер Dto каждый час.
 */
@Component
@AllArgsConstructor
@Log4j2
public class ServiceExternalEmployeeImp implements ServiceExternalEmployee {

    private final ObjectMapper objectMapper;
    private final EurekaClient eurekaClient;

    /*Удалить*/
    @LoadBalanced
    private final RestTemplate restTemplate;
    private final JobScheduler jobScheduler;
    private final ConvertEmployee convertEmployee;


    @Override
    @Scheduled(cron = "${cron.employee}")
    public void dataSyncMethodIsRunningItRunsEveryHour() {
        log.info("The data synchronization method has started, it starts every hour");
        String json = restTemplate.getForObject(jobScheduler.getEmployeeUrl(), String.class);
        log.info("Received JSON from the client");
        int random = new Random().nextInt(eurekaClient.getApplication(EDO_REPOSITORY_NAME).getInstances().size());
        log.info("Created a random number , for instances");
        System.out.println(random);
        String host = eurekaClient.getApplication(EDO_REPOSITORY_NAME).getInstances().get(random).getHostName();

        String urlRestEmployee = UriBuilderUtil.getUrlEmployeeDepartment(HTTP, host, random, URL_EMPLOYEE_PATH, eurekaClient
                .getApplication(EDO_REPOSITORY_NAME).getInstances().get(random).getPort());

        String urlRestDepartment = UriBuilderUtil.getUrlEmployeeDepartment(HTTP, host, random, URL_DEPARTMENT_PATH, eurekaClient
                .getApplication(EDO_REPOSITORY_NAME).getInstances().get(random).getPort());
        log.info("Using Eureka Client, we created a URL for requests via RestTemplate");


        /**Преобразование JSON с помощью ObjectMapper в объект ExternalEmployeeDto*/

        List<ExternalEmployeeDto> externalEmployeesDto = null;
        try {
            externalEmployeesDto = objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("Converted JSON using ObjectMapper into an object ExternalEmployeeDto ");

        /**Преобразование ExternalEmployeeDto в employeeDto, направил на контроллер
         * сохранения и заархивировал ExternalEmployeeDto, поле которого IsDeleted = true*/
        for (ExternalEmployeeDto externalEmployeeDto : externalEmployeesDto) {

            EmployeeDto employeeDto = restTemplate.postForObject(urlRestEmployee, convertEmployee.toDto(externalEmployeeDto), EmployeeDto.class);
            if (externalEmployeeDto.isDelete()) {
                restTemplate.postForObject(urlRestEmployee + "/" + employeeDto.getId(), null, String.class);
            }
            if (externalEmployeeDto.getCompany().isDelete()) {
                restTemplate.postForObject(urlRestDepartment + "/" + employeeDto.getDepartment().getId(), null, String.class);
            }
        }
        log.info("converted an external user into That, directed to the save controller and archived an external user whose field IsDeleted = true");

    }


}
