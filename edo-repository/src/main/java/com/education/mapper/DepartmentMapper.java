package com.education.mapper;

import com.education.entity.Department;
import model.dto.DepartmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.sql.Timestamp;
import java.time.Instant;


@Mapper
public interface
DepartmentMapper {
    public static DepartmentMapper mapper = Mappers.getMapper(DepartmentMapper.class);
    DepartmentDto  toDTO(Department department);
    Department toDep(DepartmentDto departmentDto);

    default Timestamp map(Instant instant) {
        return instant == null ? null : Timestamp.from(instant);
    }

}