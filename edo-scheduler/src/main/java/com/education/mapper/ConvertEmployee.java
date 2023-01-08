package com.education.mapper;


import model.dto.AddressDto;
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
                .firstName(externalEmployeeDto.getName().getFirst())
                .lastName(externalEmployeeDto.getName().getLast())
                .middleName(externalEmployeeDto.getName().getMiddle())
                .address(AddressDto.builder()
                        .street(externalEmployeeDto
                        .getLocation()
                        .getStreet()
                        .getName())
                        .index(externalEmployeeDto.getLocation().getPostcode())
                        .city(externalEmployeeDto.getLocation().getCity())
                        .region(externalEmployeeDto.getLocation().getState())
                        .country(externalEmployeeDto.getLocation().getCountry())
                        .build())
                .phone(externalEmployeeDto.getPhone())
                .workPhone(externalEmployeeDto.getCell())
                .birthDate(externalEmployeeDto.getDob().getDate())
                .username(externalEmployeeDto.getLogin().getUsername())
                .build();
    }
}
