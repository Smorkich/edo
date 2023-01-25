package com.education.mapper;


import model.dto.AddressDto;
import model.dto.DepartmentDto;
import model.dto.EmployeeDto;
import model.dto.ExternalEmployeeDto;
import org.springframework.stereotype.Component;

/**
 * @author Usolkin Dmitry
 * Конвертация ExternalEmployeeDto в EmployeeDto
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
                        .latitude(externalEmployeeDto.getLocation().getCoordinates().getLatitude())
                        .longitude(externalEmployeeDto.getLocation().getCoordinates().getLongitude())
                        .build())
                .phone(externalEmployeeDto.getPhone())
                .workPhone(externalEmployeeDto.getCell())
                .birthDate(externalEmployeeDto.getDob().getDate())
                .username(externalEmployeeDto.getLogin().getUsername())
                .externalId(externalEmployeeDto.getId().getValue())
                .department(DepartmentDto.builder()
                        .shortName(externalEmployeeDto.getCompany().getName())
                        .fullName(externalEmployeeDto.getCompany().getName())
                        .address(AddressDto.builder()
                                .street(externalEmployeeDto
                                        .getCompany()
                                        .getLocation()
                                        .getStreet()
                                        .getName())
                                .index(externalEmployeeDto.getCompany().getLocation().getPostcode())
                                .city(externalEmployeeDto.getCompany().getLocation().getCity())
                                .region(externalEmployeeDto.getCompany().getLocation().getState())
                                .country(externalEmployeeDto.getCompany().getLocation().getCountry())
                                .latitude(externalEmployeeDto.getCompany().getLocation().getCoordinates().getLatitude())
                                .longitude(externalEmployeeDto.getCompany().getLocation().getCoordinates().getLongitude())
                                .build())
                        .phone(externalEmployeeDto.getPhone())
                        .externalId(externalEmployeeDto.getCompany().getId().getValue())
                        .build())
                .build();
    }
}
