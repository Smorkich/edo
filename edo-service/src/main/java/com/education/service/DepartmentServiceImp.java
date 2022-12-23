package com.education.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.DepartmentDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Usolkin Dmitry
 * Сервис, который соединяет реализацию двух модулей через RestTemplate
 * Имеет все те же операции, что и service в edo-repository
 */
@Service
public class DepartmentServiceImp implements DepartmentService {
    private static final String URL = "http://edo-repository/api/repository/department";

    private static final Logger LOGGER = Logger.getLogger(DepartmentServiceImp.class.getName());
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public DepartmentServiceImp(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void save(DepartmentDto department) {
        restTemplate.postForObject(URL, department, String.class);
        LOGGER.info("sent a request to save the department in edo - repository");
    }

    @Override
    public void removeToArchived(Long id) {
        restTemplate.postForObject(URL + "/" + id, null, String.class);
        LOGGER.info("sent a request to archive the department in edo - repository");
    }

    @Override

    public DepartmentDto findById(Long id) {
        LOGGER.info("sent a request to receive the department in edo - repository");
        return restTemplate.getForObject(URL + "/" + id, DepartmentDto.class);
    }

    @Override

    public List<DepartmentDto> findByAllId(String ids) {
        LOGGER.info("sent a request to receive the departments in edo - repository");
        return restTemplate.getForObject(URL + "/all/" + ids, List.class);
    }

    @Override
    public DepartmentDto findByIdNotArchived(Long id) {
        LOGGER.info("sent a request to receive the department not archived in edo - repository");
        return restTemplate.getForObject(URL + "/NotArchived/" + id, DepartmentDto.class);

    }

    @Override
    public List<DepartmentDto> findByAllIdNotArchived(String ids) {
        LOGGER.info("sent a request to receive the departments not archived in edo - repository");
        return restTemplate.getForObject(URL + "/NotArchivedAll/" + ids, List.class);
    }
}
