package com.education.service.emloyee.impl;

import com.education.service.emloyee.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;


/**
 * @author Kiladze George
 * Сервис, который соединяет реализацию двух модулей через RestTemplate
 * Имеет все те же операции, что и service в edo-repository
 */
@Service
@Log4j2
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    static final String URL = "http://edo-repository/api/repository/employee";
    private final RestTemplate restTemplate;

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        log.info("Sent a request to save the employee in edo - repository");
        return restTemplate.postForObject(URL, employeeDto, EmployeeDto.class);
    }

    @Override
    public void moveToArchived(Long id) {
        restTemplate.postForObject(URL + "/" + id, null, String.class);
        log.info("Sent a request to archive the employee in edo - repository");
    }

    @Override
    public EmployeeDto findById(Long id) {
        log.info("Sent a request to receive the employee in edo - repository");
        return restTemplate.getForObject(URL + "/" + id, EmployeeDto.class);
    }

    @Override
    public Collection<EmployeeDto> findAll() {
        log.info("Sent a request to receive all employee in edo - repository");
        return restTemplate.getForObject(URL + "/all", List.class);
    }

    @Override
    public Collection<EmployeeDto> findAllById(String ids) {
        log.info("Sent a request to receive the employee in edo - repository");
        return restTemplate.getForObject(URL + "/all/" + ids, List.class);
    }

    @Override
    public EmployeeDto findByIdAndArchivedDateNull(Long id) {
        log.info("Sent a request to receive the employee archived in edo - repository");
        return restTemplate.getForObject(URL + "/notArchived/" + id, EmployeeDto.class);
    }

    @Override
    public Collection<EmployeeDto> findByIdInAndArchivedDateNull(String ids) {
        log.info("Sent a request to receive the employee not archived in edo - repository");
        return restTemplate.getForObject(URL + "/notArchivedAll/" + ids, List.class);
    }

    /**
     * Сохраняет коллекцию сотрудников
     */
    @Override
    public Collection<EmployeeDto> saveCollection(Collection<EmployeeDto> employeeDto) {
        log.info("Sent a request to to save a collection of employees, in edo - repository");
        return restTemplate.postForObject(URL + "/collection", employeeDto, Collection.class);
    }
}
