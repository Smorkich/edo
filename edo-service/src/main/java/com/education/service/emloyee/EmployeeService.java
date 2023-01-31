package com.education.service.emloyee;

import model.dto.EmployeeDto;

import java.net.URISyntaxException;
import java.util.Collection;

/**
 * @author Kiladze George
 * интерфейс сервиса в edo-service
 */
public interface EmployeeService {

    void save(EmployeeDto employeeDto) throws URISyntaxException;

    void moveToArchived(Long id) throws URISyntaxException;

    EmployeeDto findById(Long id) throws URISyntaxException;

    Collection<EmployeeDto> findAll() throws URISyntaxException;

    Collection<EmployeeDto> findAllById(String ids) throws URISyntaxException;

    EmployeeDto findByIdAndArchivedDateNull(Long id) throws URISyntaxException;

    Collection<EmployeeDto> findByIdInAndArchivedDateNull(String ids) throws URISyntaxException;

    Collection<EmployeeDto> findAllByFullName(String fullName) throws URISyntaxException;
}
