package com.education.mapper;

import com.education.entity.Facsimile;
import model.dto.FacsimileDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для преобразования Facsimile к FacsimileDto
 */
@Mapper
public interface FacsimileMapper extends AbstractMapper<Facsimile, FacsimileDto> {
    FacsimileMapper FACSIMILE_MAPPER = Mappers.getMapper(FacsimileMapper.class);
}
