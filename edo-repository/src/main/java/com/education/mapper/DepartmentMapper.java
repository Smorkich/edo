package com.education.mapper;

import com.education.entity.Department;
import model.dto.DepartmentDto;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Usolkin Dmitry
 * Интерфейс для реализации конвертации департамента в ДТО и обратно
 */

@Mapper(uses = AddressMapper.class)
public interface DepartmentMapper extends AbstractMapper<Department, DepartmentDto> {
    DepartmentMapper DEPARTMENT_MAPPER = Mappers.getMapper(DepartmentMapper.class);
    @BeforeMapping
    default void beforeMapping(DepartmentDto department) {
        if(department!= null && department.getDepartment().getShortName() == null
                && department.getAddress()!= null && department.getAddress().getLatitude() == null) {
            department.setDepartment(null);
            department.setAddress(null);
        }
    }


}
