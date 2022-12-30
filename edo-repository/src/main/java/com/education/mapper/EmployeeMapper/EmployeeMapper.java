package com.education.mapper.EmployeeMapper;

import com.education.entity.Employee;
import com.education.mapper.AbstractMapper;
import model.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author George Kiladze
 * Интерфейс для реализации конвертации департамента в ДТО и обратно
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends AbstractMapper<Employee, EmployeeDto> {
    EmployeeMapper EMPLOYEE_MAPPER = Mappers.getMapper(EmployeeMapper.class);
}
