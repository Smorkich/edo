package com.education.mapper;


import model.dto.EmployeeDto;
import model.dto.ExternalEmployeeDto;
import org.springframework.stereotype.Component;

/**
 * @author
 */
@Component
public class ConvertEmployee {
    public EmployeeDto toDto(ExternalEmployeeDto externalEmployeeDto) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName(externalEmployeeDto.getFirstName());
        employeeDto.setLastName(externalEmployeeDto.getLastName());
        employeeDto.setMiddleName(externalEmployeeDto.getMiddleName());
        employeeDto.setAddress(externalEmployeeDto.getAddress());
        employeeDto.setFioDative(externalEmployeeDto.getFioDative());
        employeeDto.setFioNominative(externalEmployeeDto.getFioNominative());
        employeeDto.setFioGenitive(externalEmployeeDto.getFioGenitive());
        employeeDto.setExternalId(externalEmployeeDto.getExternalId());
        employeeDto.setPhone(externalEmployeeDto.getPhone());
        employeeDto.setWorkPhone(externalEmployeeDto.getWorkPhone());
        employeeDto.setBirthDate(externalEmployeeDto.getBirthDate());
        employeeDto.setUsername(employeeDto.getUsername());
        return  employeeDto;
    }
}
