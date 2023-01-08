package com.education.mapper.EmployeeMapper;

import com.education.entity.Employee;
import com.education.mapper.AbstractMapper;
import com.education.mapper.AddressMapper;
import com.education.mapper.DepartmentMapper;
import model.dto.DepartmentDto;
import model.dto.EmployeeDto;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author George Kiladze
 * Интерфейс для реализации конвертации департамента в ДТО и обратно
 */
@Mapper(uses = {AddressMapper.class, DepartmentMapper.class})
public interface EmployeeMapper extends AbstractMapper<Employee, EmployeeDto> {
    EmployeeMapper EMPLOYEE_MAPPER = Mappers.getMapper(EmployeeMapper.class);

    @BeforeMapping
    default void beforeMapping(EmployeeDto employeeDto) {
        if(employeeDto!= null && employeeDto.getDepartment().getShortName() == null
                && employeeDto.getAddress()!= null && employeeDto.getAddress().getLatitude() == null) {
            employeeDto.setDepartment(null);
            employeeDto.setAddress(null);
        }
    }
}
