package com.education.mapper;

import com.education.entity.Region;
import model.dto.RegionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для реализации конвертации региона в ДТО и обратно
 */
@Mapper
public interface RegionMapper extends AbstractMapper<Region, RegionDto> {
    RegionMapper REGION_MAPPER = Mappers.getMapper(RegionMapper.class);
}
