package com.education.service.employee.impl;

import com.education.service.employee.EmployeeService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;

import static model.constant.Constant.EDO_SERVICE_NAME;
import static model.constant.Constant.EMPLOYEE_FIO_SEARCH_PARAMETER;


/**
 * @author Kryukov Andrey
 * Сервис направляет запрос в edo-service
 */
@Service
@Log4j2
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final RestTemplate restTemplate;

    @Override
    public Collection<EmployeeDto> findAllByFullName(String fullName) {
        log.info("Build uri to service: {}", fullName);
        URL uri = null;
        try {
            uri = URIBuilderUtil.buildURI(EDO_SERVICE_NAME, "/api/service/employee/search")
                    .addParameter(EMPLOYEE_FIO_SEARCH_PARAMETER, fullName).build().toURL();
            log.info("Sent a request to receive the employee collection with requested full name");
            return restTemplate.getForObject(uri.toURI(), Collection.class);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}
