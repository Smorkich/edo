package com.education.service.employee.impl;

import com.education.service.employee.EmployeeService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;


/**
 * @author Kryukov Andrey
 * Сервис направляет запрос в edo-service
 */
@Service
@Log4j2
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final RestTemplate restTemplate;
    private final URIBuilderUtil uriBuilder;

    @Override
    public Collection<EmployeeDto> findAllByFullName(String fullName) {
        String uri = uriBuilder.buildURI("edo-service", "/api/service/employee/search")
                .addParameter("fullName", fullName).toString();
        log.info("URI with full name parameter built");
        Collection<EmployeeDto> employeeDtoList = restTemplate.getForObject(uri, Collection.class);
        log.info("Taken employee list from service by rest template");
        return employeeDtoList;
    }
}
