package com.education.service.appeal.impl;

import com.education.entity.Appeal;
import com.education.repository.appeal.AppealRepository;
import com.education.service.appeal.AllAppealService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Collection;

/**
 * Сервис-слой для сущности Appeal
 */
@Service
@AllArgsConstructor
public class AllAppealServiceImpl implements AllAppealService {

    private AppealRepository appealRepository;

    /**
     * Нахождение всех обращений c пагинацией
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Appeal> findAllWithPagination(int lastUser, int numberOfUsersToDisplay) {
        Pageable pageable = (Pageable) PageRequest.of(lastUser, numberOfUsersToDisplay, Sort.by("id").ascending());
        return appealRepository.findAllByOrderByIdAsc(pageable);
    }

}