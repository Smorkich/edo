package com.education.service.execution;

import com.education.entity.ExecutionReport;

import java.util.List;
import java.util.Map;

public interface ExecutorReportService {
    ExecutionReport submitReport(ExecutionReport reportDto);

    /**
     * Receive resolution statuses
     */
    Map<Long, String> getResolutionStatuses(List<Long> resolutionId);
}