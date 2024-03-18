package com.education.service.deadlineResolution.impl;

import com.education.entity.DeadlineResolution;
import com.education.repository.deadlineResolution.DeadlineResolutionRepository;
import com.education.service.deadlineResolution.DeadlineResolutionService;
import lombok.AllArgsConstructor;
import model.dto.EmailAndIdDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
    public void saveDeadlineResolution(DeadlineResolution deadlineResolution) {
        deadlineResolutionRepository.save(deadlineResolution);
    }

    @Override
    @Transactional
    public List<EmailAndIdDto> findAllExecutorEmails() {
        List<Object[]> executorEmails = deadlineResolutionRepository.findAllExecutorEmails();
        return executorEmails.stream().map(EmailAndIdDto::new).collect(toList());
    }
}
