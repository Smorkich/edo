package com.education.mapper;

import com.education.entity.ExecutionReport;
import model.dto.ExecutionReportDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для преобразования ResolutionExecutionReport к ResolutionExecutionReportDto
 */
@Mapper
public interface ExecutionReportMapper extends AbstractMapper<ExecutionReport, ExecutionReportDto> {
    ExecutionReportMapper EXECUTION_REPORT_MAPPER = Mappers.getMapper(ExecutionReportMapper.class);
}
