package com.education.service.department.imp;

import com.education.entity.Department;
import com.education.repository.department.DepartmentRepository;
import com.education.service.department.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * @author Usolkin Dmitry
 * Сервис, который использует методы Repository
 * Сервис рабоатет с сущностью Department
 * Добавляет, архивирует
 * достает по id департамент,несколько департаментов и департаменты,
 * которые не имеют  дату архивации
 */

@Service
@AllArgsConstructor
public class DepartmentServiceImp implements DepartmentService {
    private final DepartmentRepository repository;

    /**
     * добавляет департамент
     *
     * @param department
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Department department) {
        department.setCreationDate(ZonedDateTime.now());
        repository.save(department);
    }

    /**
     * заносит дату архивации, тем самым арххивируя департамент
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeToArchived(Long id) {
        repository.removeToArchived(ZonedDateTime.now(),id);
    }

    /**
     * достает департамент по id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Department findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * достает департамент по нескольким id
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Department> findByAllId(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }

    /**
     * достает департамент без даты архивации по id
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Department findByIdNotArchived(Long id) {
        return repository.findByIdAndArchivedDateNull(id);
    }

    /**
     * достает департаменты без даты архивации по нескольким id
     *
     * @param ids
     * @return
     */
    @Transactional(readOnly = true)
    public List<Department> findByAllIdNotArchived(Iterable<Long> ids) {
        return repository.findByIdInAndArchivedDateNull(ids);
    }
}
