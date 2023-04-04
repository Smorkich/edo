package com.education.mapper;

import com.education.entity.FederalDistrict;
import model.dto.FederalDistrictDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для реализации конвертации региона в ДТО и обратно
 */
@Mapper
public interface FederalDistrictMapper extends AbstractMapper<FederalDistrict, FederalDistrictDto> {
    FederalDistrictMapper FEDERAL_DISTRICT_MAPPER = Mappers.getMapper(FederalDistrictMapper.class);
}
