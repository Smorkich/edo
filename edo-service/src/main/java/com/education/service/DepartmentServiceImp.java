package com.education.service;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.DepartmentDto;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * @author Usolkin Dmitry
 * Сервис, который соединяет реализацию двух модулей через RestTemplate
 * Имеет все те же операции, что и service в edo-repository
 */
@Service
@Log4j2
@AllArgsConstructor
public class DepartmentServiceImp implements DepartmentService {
    private static final String URL = "http://edo-repository/api/repository/department";

    private final RestTemplate restTemplate;



    @Override
    public void save(DepartmentDto department) {
        restTemplate.postForObject(URL, department, String.class);
        log.info("sent a request to save the department in edo - repository");
    }

    @Override
    public void removeToArchived(Long id) {
        restTemplate.postForObject(URL + "/" + id, null, String.class);
        log.info("sent a request to archive the department in edo - repository");
    }

    @Override
    public DepartmentDto findById(Long id) {
        log.info("sent a request to receive the department in edo - repository");
        return restTemplate.getForObject(URL + "/" + id, DepartmentDto.class);
    }

    @Override
    public List<DepartmentDto> findByAllId(String ids) {
        log.info("sent a request to receive the departments in edo - repository");
        return restTemplate.getForObject(URL + "/all/" + ids, List.class);
    }

    @Override
    public DepartmentDto findByIdNotArchived(Long id) {
        log.info("sent a request to receive the department not archived in edo - repository");
        return restTemplate.getForObject(URL + "/notArchived/" + id, DepartmentDto.class);

    }

    @Override
    public List<DepartmentDto> findByAllIdNotArchived(String ids) {
        log.info("sent a request to receive the departments not archived in edo - repository");
        return restTemplate.getForObject(URL + "/notArchivedAll/" + ids, List.class);
    }
}
