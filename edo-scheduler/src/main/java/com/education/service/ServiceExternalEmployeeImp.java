package com.education.service;


import com.education.job.JobScheduler;
import com.education.mapper.ConvertEmployee;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import model.dto.ExternalEmployeeDto;
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


    private final RestTemplate restTemplate;
    private final JobScheduler jobScheduler;
    private final ConvertEmployee convertEmployee;
    @Scheduled(cron = "${cron-expression.employee}")
    @Override
    public void save() {
        log.info("The data synchronization method has started, it starts every hour");
        List<ExternalEmployeeDto> externalEmployeesDto = restTemplate.getForObject(jobScheduler.getEmployeeUrl(),List.class);
        log.info("got an external employee from the andpoint");
        for(ExternalEmployeeDto externalEmployeeDto : externalEmployeesDto) {
            EmployeeDto employeeDto = convertEmployee.toDto(externalEmployeeDto);
            Long idSaveDepartment = restTemplate.postForObject("http://edo-repository/api/repository/employee",employeeDto, Long.class);
            if(externalEmployeeDto.isDelete()) {
                restTemplate.postForObject("http://edo-repository/api/repository/employee" + idSaveDepartment ,null, String.class);
            }
        }
        log.info("converted an external user into That, directed to the save controller and archived an external user whose field IsDeleted = true");

    }

}
