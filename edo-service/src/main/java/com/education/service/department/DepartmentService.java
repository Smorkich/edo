package com.education.service.department;


import model.dto.DepartmentDto;

import java.net.URISyntaxException;
import java.util.Collection;


/**
 * @author Usolkin Dmitry
 * Интерфейс сервиса в edo-service
 */
public interface DepartmentService {

    void save(DepartmentDto departmentDto) throws URISyntaxException;

    void removeToArchived(Long id) throws URISyntaxException;

    DepartmentDto findById(Long id) throws URISyntaxException;

    Collection<DepartmentDto> findByAllId(String ids) throws URISyntaxException;

    DepartmentDto findByIdNotArchived(Long id) throws URISyntaxException;

    Collection<DepartmentDto> findByAllIdNotArchived(String ids) throws URISyntaxException;

}
