package com.education.mapper;


import com.education.entity.Department;
import model.dto.DepartmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;



/**
 * @author Usolkin Dmitry
 * Интерфейс для реализации конвертации департамента в ДТО и обратно
 */
@Mapper
public interface
DepartmentMapper {
    public static DepartmentMapper mapper = Mappers.getMapper(DepartmentMapper.class);

    DepartmentDto toDTO(Department department);

    Department toDep(DepartmentDto departmentDto);


}