package com.education.service.execution.impl;

import com.education.entity.ExecutionReport;
import com.education.repository.executor.ExecutionReportRepository;
import com.education.service.execution.ExecutorReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nikita Sheikin
 * Сервис слой для ExecutionReport
 */

@AllArgsConstructor
@Service
public class ExecutorReportServiceImpl implements ExecutorReportService {
    private final ExecutionReportRepository repository;

    /**
     * Метод отправляющий ExecutionReportDto в edo-repository на сохранение
     *
     * @param executionReport - отчет по резолюции
     * @return ExecutionReport
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExecutionReport submitReport(ExecutionReport executionReport) {
        return repository.saveAndFlush(executionReport);
    }

    /**
     * Receive resolution status
     */
    @Override
    @Transactional(readOnly = true)
    public String resolutionStatus(Long resolutionId) {
        return repository.resolutionStatus(resolutionId);
    }
}