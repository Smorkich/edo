package com.education.service.employee.impl;

import com.education.entity.Address;
import com.education.entity.Department;
import com.education.entity.Employee;
import com.education.repository.employee.EmployeeRepository;
import com.education.service.address.AddressService;
import com.education.service.department.DepartmentService;
import com.education.service.employee.EmployeeService;
import com.github.aleksandy.petrovich.Case;
import com.github.aleksandy.petrovich.Petrovich;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    private final AddressService addressService;
    private final DepartmentService departmentService;

    /**
     * добавляет сотрудника
     *
     * @param employee
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(Employee employee) {

        employee.setCreationDate(ZonedDateTime.now());
        Petrovich.Names names = new Petrovich.Names(employee.getLastName(), employee.getFirstName(), employee.getMiddleName(), null);
        Petrovich petrovich = new Petrovich();
        String fioDative = petrovich.inflectTo(names, Case.DATIVE).lastName
                .concat(StringUtils.SPACE)
                .concat(petrovich.inflectTo(names, Case.DATIVE).firstName)
                .concat(StringUtils.SPACE)
                .concat(petrovich.inflectTo(names, Case.DATIVE).middleName);
        employee.setFioDative(fioDative);

        String fioGenitive = petrovich.inflectTo(names, Case.GENITIVE).lastName
                .concat(StringUtils.SPACE)
                .concat(petrovich.inflectTo(names, Case.GENITIVE).firstName)
                .concat(StringUtils.SPACE)
                .concat(petrovich.inflectTo(names, Case.GENITIVE).middleName);
        employee.setFioGenitive(fioGenitive);

        String fioNominative = petrovich.inflectTo(names, Case.NOMINATIVE).lastName
                .concat(StringUtils.SPACE)
                .concat(petrovich.inflectTo(names, Case.NOMINATIVE).firstName)
                .concat(StringUtils.SPACE)
                .concat(petrovich.inflectTo(names, Case.NOMINATIVE).middleName);
        employee.setFioNominative(fioNominative);
        employee.getDepartment().setCreationDate(ZonedDateTime.now());

        employeeRepository.save(employee);

        return employee.getId();
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
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        employeeRepository.moveToArchived(zonedDateTime, id);
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
     * Сохранение коллекции сотрудников
     *
     * @param employees
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Employee> saveCollection (Collection<Employee> employees) {
//        employees.forEach(employee -> {
//            employee.setCreationDate(ZonedDateTime.now());
//            Petrovich.Names names = new Petrovich.Names(employee.getLastName(), employee.getFirstName(), employee.getMiddleName(), null);
//            Petrovich petrovich = new Petrovich();
//            String fioDative = petrovich.inflectTo(names, Case.DATIVE).lastName
//                    .concat(StringUtils.SPACE)
//                    .concat(petrovich.inflectTo(names, Case.DATIVE).firstName)
//                    .concat(StringUtils.SPACE)
//                    .concat(petrovich.inflectTo(names, Case.DATIVE).middleName);
//            employee.setFioDative(fioDative);
//
//            String fioGenitive = petrovich.inflectTo(names, Case.GENITIVE).lastName
//                    .concat(StringUtils.SPACE)
//                    .concat(petrovich.inflectTo(names, Case.GENITIVE).firstName)
//                    .concat(StringUtils.SPACE)
//                    .concat(petrovich.inflectTo(names, Case.GENITIVE).middleName);
//            employee.setFioGenitive(fioGenitive);
//
//            String fioNominative = petrovich.inflectTo(names, Case.NOMINATIVE).lastName
//                    .concat(StringUtils.SPACE)
//                    .concat(petrovich.inflectTo(names, Case.NOMINATIVE).firstName)
//                    .concat(StringUtils.SPACE)
//                    .concat(petrovich.inflectTo(names, Case.NOMINATIVE).middleName);
//            employee.setFioNominative(fioNominative);
//            employee.getDepartment().setCreationDate(ZonedDateTime.now());
//        });

        Collection<Address> addresses = new ArrayList<>();
        Collection<Department> departments = new ArrayList<>();

        employees.forEach(employee -> {
            addresses.add(employee.getAddress());
            departments.add(employee.getDepartment());
        });

        addressService.saveCollection(addresses);
        departmentService.saveCollection(departments);

//        Collection<Address> departmentsAddresses = new ArrayList<>();
//        Collection<Department> departmentsDepartments = new ArrayList<>();
//
//        departments.forEach(department -> {
//            departmentsAddresses.add(department.getAddress());
//            departmentsDepartments.add(department.getDepartment());
//        });
//


        return employeeRepository.saveAll(employees);
    }
}
