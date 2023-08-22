package com.education.service.emloyee.impl;

import com.education.feign.EmployeeFeignClient;
import com.education.service.emloyee.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.EmployeeDto;
import org.springframework.stereotype.Service;
import java.util.Collection;


/**
 * @author Kiladze George & Kryukov Andrey
 * Сервис, который соединяет реализацию двух модулей через RestTemplate
 * Имеет все те же операции, что и service в edo-repository
 */
@Service
@Log4j2
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeFeignClient employeeFeignClient;
    @Override
    public void save(EmployeeDto employeeDto) {
        employeeFeignClient.save(employeeDto);
    }

    @Override
    public void moveToArchived(Long id)  {
        employeeFeignClient.moveToArchive(id);
    }

    @Override
    public EmployeeDto findById(Long id) {
        return employeeFeignClient.findById(id);
    }

    @Override
    public Collection<EmployeeDto> findAll() {
        return employeeFeignClient.findAll();
    }

    @Override
    public Collection<EmployeeDto> findAllById(String ids)  {
        return employeeFeignClient.findAllById(ids);
    }

    @Override
    public EmployeeDto findByIdAndArchivedDateNull(Long id) {
        return employeeFeignClient.findByIdNotArchived(id);
    }

    @Override
    public Collection<EmployeeDto> findByIdInAndArchivedDateNull(String ids)  {
        return employeeFeignClient.findByAllIdNotArchived(ids);
    }

    @Override
    public Collection<EmployeeDto> findAllByFullName(String fullName) {
        return employeeFeignClient.findAllByFullName(fullName);
    }

    /**
     * Сохраняет коллекцию сотрудников
     */
    @Override
    public Collection<EmployeeDto> saveCollection(Collection<EmployeeDto> employeeDto) {
        return employeeFeignClient.saveCollection(employeeDto);
    }
}
