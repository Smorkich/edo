package com.education.mapper;

import com.education.entity.DeadlineResolution;
import model.dto.DeadlineResolutionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для реализации конвертации DeadlineResolution в ДТО и обратно
 */
@Mapper()
public interface DeadlineResolutionMapper extends AbstractMapper<DeadlineResolution, DeadlineResolutionDto> {
    DeadlineResolutionMapper DEADLINE_RESOLUTION_MAPPER = Mappers.getMapper(DeadlineResolutionMapper.class);
}
