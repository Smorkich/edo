package com.education.service.execution;

import com.education.entity.ExecutionReport;

public interface ExecutorReportService {
    ExecutionReport submitReport(ExecutionReport reportDto);
}