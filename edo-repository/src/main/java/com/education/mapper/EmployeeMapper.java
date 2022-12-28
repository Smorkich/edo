package com.education.mapper;

import com.education.entity.Employee;
import model.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для реализации конвертации департамента в ДТО и обратно
 */

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends AbstractMapper<Employee, EmployeeDto> {
    EmployeeMapper DEPARTMENT_MAPPER = Mappers.getMapper(EmployeeMapper.class);

}
