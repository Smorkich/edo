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
import model.dto.AddressDto;
import model.dto.DepartmentDto;
import model.dto.EmployeeDto;
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
        petrovichConstructor(employee);
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
     * @param employeeDtos
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Employee> saveCollection (Collection<EmployeeDto> employeeDtos) {

        Collection<Employee> employees = new ArrayList<>();
        Collection<Address> addresses = new ArrayList<>();
        Collection<Department> departments = new ArrayList<>();

        employeeDtos.forEach(employeeDto -> {
            Employee employee = toEntity(employeeDto);
            employee.setCreationDate(ZonedDateTime.now());
            petrovichConstructor(employee);
            employee.getDepartment().setCreationDate(ZonedDateTime.now());
            employee.getDepartment().setDepartment(null);
            addresses.add(employee.getAddress());
            departments.add(employee.getDepartment());
            employees.add(employee);
        });

        addressService.saveCollection(addresses);
        departmentService.saveCollection(departments);

        return employeeRepository.saveAll(employees);
    }

    /**
     * Конструктор падежей
     *
     * @param employee
     * @return
     */
    private Employee petrovichConstructor(Employee employee) {
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
        return employee;
    }

    /**
     * Маппинг Entity из EntityDto
     *
     * @param employeeDto
     * @return
     */
    private Employee toEntity(EmployeeDto employeeDto) {
        AddressDto address = employeeDto.getAddress();
        DepartmentDto department = employeeDto.getDepartment();
        AddressDto departmentAddress = department.getAddress();
        Employee builtEmployee = Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .middleName(employeeDto.getMiddleName())
                .address(Address.builder()
                        .fullAddress(address.getFullAddress())
                        .street(address.getStreet())
                        .house(address.getHouse())
                        .index(address.getIndex())
                        .housing(address.getHousing())
                        .building(address.getBuilding())
                        .city(address.getCity())
                        .region(address.getRegion())
                        .country(address.getCountry())
                        .flat(address.getFlat())
                        .longitude(address.getLongitude())
                        .latitude(address.getLatitude()).build())
                .phone(employeeDto.getPhone())
                .workPhone(employeeDto.getWorkPhone())
                .birthDate(employeeDto.getBirthDate())
                .username(employeeDto.getUsername())
                .archivedDate(employeeDto.getArchivedDate())
                .department(Department.builder()
                        .shortName(department.getShortName())
                        .fullName(department.getFullName())
                        .address(Address.builder()
                                .fullAddress(departmentAddress.getFullAddress())
                                .street(departmentAddress.getStreet())
                                .house(departmentAddress.getHouse())
                                .index(departmentAddress.getIndex())
                                .housing(departmentAddress.getHousing())
                                .building(departmentAddress.getBuilding())
                                .city(departmentAddress.getCity())
                                .region(departmentAddress.getRegion())
                                .country(departmentAddress.getCountry())
                                .flat(departmentAddress.getFlat())
                                .longitude(departmentAddress.getLongitude())
                                .latitude(departmentAddress.getLatitude()).build())
                        .phone(department.getPhone())
                        .archivedDate(department.getArchivedDate()).build()).build();
        return builtEmployee;
    }

    /**
     * Маппинг коллекции Entity из EntityDto
     *
     * @param employeeDtos
     * @return
     */
    private Collection<Employee> toEntity(Collection<EmployeeDto> employeeDtos) {
        return employeeDtos.stream().map(this::toEntity).toList();
    }
}
