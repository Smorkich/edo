package com.education.service.deadlineResolution.impl;

import com.education.entity.DeadlineResolution;
import com.education.repository.deadlineResolution.DeadlineResolutionRepository;
import com.education.service.deadlineResolution.DeadlineResolutionService;
import lombok.AllArgsConstructor;
import model.dto.EmailAndIdDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DeadlineResolutionServiceImpl implements DeadlineResolutionService {

    private final DeadlineResolutionRepository deadlineResolutionRepository;

    @Transactional(readOnly = true)
    @Override
    public Collection<DeadlineResolution> findByResolutionId(Long resolutionId) {
        return deadlineResolutionRepository.findByResolutionId(resolutionId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveDeadlineResolution(DeadlineResolution deadlineResolution) {
        deadlineResolutionRepository.save(deadlineResolution);
    }

    @Override
    @Transactional
    public List<EmailAndIdDto> findAllExecutorEmails() {
        return deadlineResolutionRepository.findAllExecutorEmails();
    }

    @Transactional(readOnly = true)
    @Override
    public Map<Long, LocalDate> findLastDeadlinesByResolutionsId(List<Long> resolutionId) {
        return deadlineResolutionRepository.findLastDeadlineByResolutionId(resolutionId).stream()
                .collect(Collectors.toMap(
                        dl -> dl.getResolution().getId(),
                        DeadlineResolution::getDeadline,
                        (dl1, dl2) -> dl1.compareTo(dl2) >= 1 ? dl1 : dl2
                ));
    }
}
