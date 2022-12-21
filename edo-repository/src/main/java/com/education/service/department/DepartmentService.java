package com.education.service.department;

import com.education.entity.Department;

import java.time.ZonedDateTime;
import java.util.List;

/**
 *@author Usolkin Dmitry
 * Интерфейс сервера
 */
public interface DepartmentService {

    void save(Department department);
    void removeToArchived(Long id);
    Department findById(Long id);
    List<Department> findByAllId(Iterable<Long> ids);
    Department findByIdNotArchived(Long id);
    List<Department> findByAllIdNotArchived(Iterable<Long> ids);

}
