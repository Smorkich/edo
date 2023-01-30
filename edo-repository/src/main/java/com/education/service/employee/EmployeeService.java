package com.education.service.employee;

import com.education.entity.Employee;

import java.net.URISyntaxException;
import java.util.Collection;

/**
 * @author Kiladze George
 * интерфейс сервиса в edo-repository
 */
public interface EmployeeService {

    void save(Employee employee) throws URISyntaxException;

    void moveToArchived(Long id) throws URISyntaxException;

    Employee findById(Long id) throws URISyntaxException;

    Collection<Employee> findAll() throws URISyntaxException;

    Collection<Employee> findAllById(Iterable<Long> ids) throws URISyntaxException;

    Employee findByIdAndArchivedDateNull(Long id) throws URISyntaxException;

    Collection<Employee> findByIdInAndArchivedDateNull(Iterable<Long> ids) throws URISyntaxException;
}
