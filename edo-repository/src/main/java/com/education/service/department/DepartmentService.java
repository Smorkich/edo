package com.education.service.department;

import com.education.entity.Department;

import java.net.URISyntaxException;
import java.util.Collection;


/**
 *@author Usolkin Dmitry
 * Интерфейс сервера
 */
public interface DepartmentService {

    void save(Department department) throws URISyntaxException;
    void removeToArchived(Long id) throws URISyntaxException;
    Department findById(Long id) throws URISyntaxException;
    Collection<Department> findByAllId(Iterable<Long> ids) throws URISyntaxException;
    Department findByIdNotArchived(Long id) throws URISyntaxException;
    Collection<Department> findByAllIdNotArchived(Iterable<Long> ids) throws URISyntaxException;

}
