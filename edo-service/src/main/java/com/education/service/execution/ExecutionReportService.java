package com.education.service.execution;

import model.dto.ExecutionReportDto;

public interface ExecutionReportService {
    ExecutionReportDto submitReport(ExecutionReportDto reportDto);
}