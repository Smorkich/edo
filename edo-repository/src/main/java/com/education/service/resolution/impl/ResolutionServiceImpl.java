package com.education.service.resolution.impl;

import com.education.entity.DeadlineResolution;
import com.education.entity.Employee;
import com.education.entity.Resolution;
import com.education.repository.resolution.ResolutionRepository;
import com.education.service.deadlineResolution.DeadlineResolutionService;
import com.education.service.execution.ExecutorReportService;
import com.education.service.resolution.ResolutionService;
import lombok.AllArgsConstructor;
import model.dto.AppealFileDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


/**
 * Класс содержащий методы для работы и связи с резолюцией
 */
@AllArgsConstructor
@Service
public class ResolutionServiceImpl implements ResolutionService {
    /**
     * Поле для связи с Resolution
     */
    private ResolutionRepository resolutionRepository;
    private DeadlineResolutionService deadlineResolutionService;
    private ExecutorReportService executorReportService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Resolution save(Resolution resolution) {
        return resolutionRepository.saveAndFlush(resolution);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveToArchive(Long id) {
        resolutionRepository.movesToArchive(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unarchiveResolution(Long resolutionId) {
        resolutionRepository.unarchiveResolution(resolutionId);
    }

    @Transactional(readOnly = true)
    @Override
    public Resolution findById(Long id) {
        return resolutionRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Resolution> findAllById(Collection<Long> id) {
        return resolutionRepository.findAllById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Resolution> findAllByAppealIdAndIsDraftFalse(Long appealId) {
        return resolutionRepository.findAllByAppealIdAndIsDraftFalse(appealId);
    }

    @Transactional(readOnly = true)
    @Override
    public Resolution findByIdNotArchived(Long id) {
        return resolutionRepository.findByIdAndArchivedDateIsNull(id);
    }


    @Transactional(readOnly = true)
    @Override
    public Collection<Resolution> findAllByIdNotArchived(Collection<Long> id) {
        return resolutionRepository.findAllByArchivedDateIsNull(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<AppealFileDto> findAllByAppealId(Long appealId) {
        return resolutionRepository.findAllByAppealId(appealId).stream()
                .map(res -> AppealFileDto.builder()
                        .id(res.getId())
                        .creationDate(res.getCreationDate())
                        .executorFIOs(res.getExecutor().stream().map(Employee::getFioNominative).toList())
                        .deadlineResolution(deadlineResolutionService.findLastDeadlineByResolutionId(res.getId())
                                .map(DeadlineResolution::getDeadline)
                                .orElse(LocalDate.of(0, 1, 1)))
                        .resolutionStatus(executorReportService.resolutionStatus(res.getId()))
                        .build()).toList();
    }
}
