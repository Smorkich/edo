package com.education.service.execution.impl;

import com.education.entity.ExecutionReport;
import com.education.repository.executor.ExecutionReportRepository;
import com.education.service.execution.ExecutorReportService;
import lombok.AllArgsConstructor;
import model.enum_.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * Receive resolution statuses
     */
    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> getResolutionStatuses(List<Long> resolutionId) {
        return repository.getResolutionStatus(resolutionId).stream()
                .filter(er -> er.getStatus().equals(Status.PERFORMED))
                .collect(Collectors.toMap(
                        er -> er.getResolution().getId(),
                        er -> "Исполнена"
                ));
    }
}