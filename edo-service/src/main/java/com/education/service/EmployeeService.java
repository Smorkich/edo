package com.education.service;

import model.dto.EmployeeDto;

import java.util.Collection;

/**
 * @author Kiladze George
 * интерфейс сервиса в edo-service
 */
public interface EmployeeService {

    void save(EmployeeDto employeeDto);

    void moveToArchived(Long id);

    EmployeeDto findById(Long id);

    Collection<EmployeeDto> findAll();

    Collection<EmployeeDto> findAllById(String ids);

    EmployeeDto findByIdAndArchivedDateNull(Long id);

    Collection<EmployeeDto> findByIdInAndArchivedDateNull(String ids);
}
