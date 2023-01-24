package com.education.service;


import com.education.job.JobScheduler;
import com.education.mapper.ConvertEmployee;
import com.education.util.URIBuilderUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import model.dto.ExternalEmployeeDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Collection;

import static model.constant.ConstantScheduler.EDO_REPOSITORY_NAME;
import static model.constant.ConstantScheduler.URL_DEPARTMENT_PATH;
import static model.constant.ConstantScheduler.URL_EMPLOYEE_SAVE_PATH;

/**
 * @author Usolkin Dmitry & Kostenko Aleksandr
 * Класс планировщик с методом, который будет нарпавлять обьект EmployeeDto в контроллер Dto каждый час.
 */
@Component
@AllArgsConstructor
@Log4j2
public class ServiceExternalEmployeeImp implements ServiceExternalEmployee {

    private final ObjectMapper objectMapper;
    private final URIBuilderUtil uriBuilderUtil;
    private final JobScheduler jobScheduler;
    private final ConvertEmployee convertEmployee;
    private final RestTemplate restTemplate;



    @Override
    //@Scheduled(cron = "${cron.employee}")
    @Scheduled(fixedDelayString = "PT04M")
    public void dataSyncEveryHour() {
        log.info("The data synchronization method has started, it starts every hour");

        Collection<ExternalEmployeeDto> externalEmployeesDto = null;
        try {
            URL url = new URL(jobScheduler.getEmployeeUrl());
            externalEmployeesDto = objectMapper.readValue(url, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String uriEmployeeSavePath = uriBuilderUtil.buildURI(EDO_REPOSITORY_NAME, URL_EMPLOYEE_SAVE_PATH).toString();
        String uriDepartmentPath = uriBuilderUtil.buildURI(EDO_REPOSITORY_NAME, URL_DEPARTMENT_PATH).toString();

        Collection<EmployeeDto> employeeDtos = externalEmployeesDto.stream().map(externalEmployee -> {
            EmployeeDto employeeDto = convertEmployee.toDto(externalEmployee);
            if (externalEmployee.isDelete()) {
                employeeDto.setArchivedDate(ZonedDateTime.now());
            }
            return employeeDto;
        }).toList();

        restTemplate.postForObject(uriEmployeeSavePath, employeeDtos,  EmployeeDto.class);
    }
}




