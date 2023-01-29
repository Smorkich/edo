package com.education.service.employee.impl;

import com.education.entity.Employee;
import com.education.repository.employee.EmployeeRepository;
import com.education.service.employee.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author Kiladze George
 * Сервис, который использует методы Repository
 * Сервис рабоатет с сущностью Employee
 * Сохраняет, архивирует
 * предоставляет по id сотрудника, всех сотрудников с указанными id,
 * всех сотрудников или сотрудниковЮ которые не имеют дату архивации
 */
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * добавляет сотрудника
     *
     * @param employee
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Employee employee) {
        employee.setCreationDate(ZonedDateTime.now());
        employeeRepository.save(employee);
    }

    /**
     * архивация сотрудника с занесением времени архивации
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveToArchived(Long id) {
        employeeRepository.moveToArchived(id);
    }

    /**
     * предоставляет сотрудника по id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    /**
     * предоставляет всех сотрудников
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> findAll() {
        return employeeRepository.findAll();
    }

    /**
     * предоставляет сотрудников по некоскольким id
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> findAllById(Iterable<Long> ids) {
        return employeeRepository.findAllById(ids);
    }

    /**
     * предоставляет заархивированного сторудника по id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Employee findByIdAndArchivedDateNull(Long id) {
        return employeeRepository.findByIdAndArchivedDateNull(id);
    }

    /**
     * предоставляет заархивированных сторудников по нескольким id
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> findByIdInAndArchivedDateNull(Iterable<Long> ids) {
        return employeeRepository.findByIdInAndArchivedDateNull(ids);
    }

    /**
     * предоставляет сторудников по ФИО
     *
     * @param fullName
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> findAllByFullName(String fullName) {
        return employeeRepository.findAllByFullName(fullName);
    }

}
