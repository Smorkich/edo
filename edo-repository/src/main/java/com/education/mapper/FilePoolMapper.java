package com.education.mapper;

import com.education.entity.FilePool;
import model.dto.FilePoolDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для реализации конвертации департамента в ДТО и обратно
 */

@Mapper(componentModel = "spring")
public interface FilePoolMapper extends AbstractMapper<FilePool, FilePoolDto> {
    FilePoolMapper DEPARTMENT_MAPPER = Mappers.getMapper(FilePoolMapper.class);

}
