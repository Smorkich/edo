package com.education.service.department;

import com.education.entity.Department;
import java.util.Collection;


/**
 *@author Usolkin Dmitry
 * Интерфейс сервера
 */
public interface DepartmentService {

    void save(Department department);
    void removeToArchived(Long id);
    Department findById(Long id);
    Collection<Department> findByAllId(Iterable<Long> ids);
    Department findByIdNotArchived(Long id);
    Collection<Department> findByAllIdNotArchived(Iterable<Long> ids);

}
