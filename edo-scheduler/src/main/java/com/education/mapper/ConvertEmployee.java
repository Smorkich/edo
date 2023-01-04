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

        return  EmployeeDto.builder()
                .firstName(externalEmployeeDto.getFirstName())
                .lastName(externalEmployeeDto.getLastName())
                .middleName(externalEmployeeDto.getMiddleName())
                .address(externalEmployeeDto.getAddress())
                .externalId(externalEmployeeDto.getExternalId())
                .phone(externalEmployeeDto.getPhone())
                .workPhone(externalEmployeeDto.getWorkPhone())
                .birthDate(externalEmployeeDto.getBirthDate())
                .username(externalEmployeeDto.getUsername())
                .build();
    }
}
