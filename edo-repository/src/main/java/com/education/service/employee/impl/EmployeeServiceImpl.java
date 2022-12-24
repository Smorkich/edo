package com.education.service.employee.impl;

import com.education.entity.Employee;
import com.education.repository.employee.EmployeeRepository;
import com.education.service.employee.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

}
