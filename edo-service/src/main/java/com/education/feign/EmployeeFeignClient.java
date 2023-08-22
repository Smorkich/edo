package com.education.feign;

import com.education.config.FeignConfig;
import model.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.EMPLOYEE_URL;

@FeignClient(name = EDO_REPOSITORY_NAME, configuration = FeignConfig.class)
public interface EmployeeFeignClient {
    @PostMapping(EMPLOYEE_URL)
    EmployeeDto save(@RequestBody EmployeeDto employeeDto);

    @PostMapping(EMPLOYEE_URL + "/{id}")
    String moveToArchive(@PathVariable(name = "id") Long id);

    @GetMapping(EMPLOYEE_URL + "/{id}")
    EmployeeDto findById(@PathVariable(name = "id") Long id);

    @GetMapping(EMPLOYEE_URL + "/all")
    Collection<EmployeeDto> findAll();

    @GetMapping(EMPLOYEE_URL + "/all/{ids}")
    Collection<EmployeeDto> findAllById(@PathVariable String ids);

    @GetMapping(value = EMPLOYEE_URL + "/notArchived/{id}")
    EmployeeDto findByIdNotArchived(@PathVariable Long id);

    @GetMapping(value = EMPLOYEE_URL + "/notArchivedAll/{ids}")
    Collection<EmployeeDto> findByAllIdNotArchived(@PathVariable String ids);

    @PostMapping(value = EMPLOYEE_URL + "/collection")
    Collection<EmployeeDto> saveCollection(@RequestBody Collection<EmployeeDto> employeeDto);

    @GetMapping(value = EMPLOYEE_URL + "/search")
    Collection<EmployeeDto> findAllByFullName(@RequestParam("fullName") String fullName);
}
