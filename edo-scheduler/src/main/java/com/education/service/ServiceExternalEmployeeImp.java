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

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.URL_EMPLOYEE_SAVE_PATH;

/**
 * @author Usolkin Dmitry & Kostenko Aleksandr & Kryukov Andrey
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

    /**
     * Каждый час синхронизирует внешних пользователей
     */
    @Override
    @Scheduled(cron = "${cron.employee}")
    public void dataSyncEveryHour() {

        log.info("The data synchronization method has started, it starts every hour");
        Collection<ExternalEmployeeDto> externalEmployeesDto;
        try {
            externalEmployeesDto = objectMapper.readValue(new URL(jobScheduler.getEmployeeUrl()), new TypeReference<>() {});
            log.info("Get all users from JSON and save to collection");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String uriEmployeeSavePath = uriBuilderUtil.buildURI(EDO_REPOSITORY_NAME, URL_EMPLOYEE_SAVE_PATH).toString();
        log.info("Got a URL to save Employee to the database");

        log.info("Started the conversion process externalEmployeeDto in EmployeeDto");
        Collection<EmployeeDto> employeeDtos = externalEmployeesDto.stream().map(externalEmployee -> {
            EmployeeDto employeeDto = convertEmployee.toDto(externalEmployee);
            /*Проверяем если пользователь удален, ставим дату архивации*/
            if (externalEmployee.isDelete()) {
                employeeDto.setArchivedDate(ZonedDateTime.now());
            }
            /*Проверяем если компания удалена, ставим дату архивации*/
            if (externalEmployee.getCompany().isDelete()) {
                employeeDto.getDepartment().setArchivedDate(ZonedDateTime.now());
            }
            return employeeDto;
        }).toList();
        log.info("Finished the conversion process externalEmployeeDto in EmployeeDto");
        restTemplate.postForObject(uriEmployeeSavePath, employeeDtos, EmployeeDto.class);
        log.info("Sent a save request to the EmployeeDto database");
    }
}




