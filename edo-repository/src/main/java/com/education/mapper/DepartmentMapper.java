package com.education.mapper;

import com.education.entity.Department;
import model.dto.DepartmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
/**
 * @author Usolkin Dmitry
 * Интерфейс для реализации конвертации департамента в ДТО и обратно
 */

@Mapper(componentModel = "spring")
public interface DepartmentMapper extends AbstractMapper<Department, DepartmentDto> {
    DepartmentMapper mapper = Mappers.getMapper(DepartmentMapper.class);

    @Override
    DepartmentDto toDto(Department entity);

    @Override
    Department toEntity(DepartmentDto dto);

    @Override
    Collection<DepartmentDto> toDto(Collection<Department> departments);

    @Override
    Collection<Department> toEntity(Collection<DepartmentDto> departmentsDto);
}
