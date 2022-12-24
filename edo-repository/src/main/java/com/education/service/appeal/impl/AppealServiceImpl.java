package com.education.service.appeal.impl;

import com.education.entity.Appeal;
import com.education.repository.appeal.AppealRepository;
import com.education.service.appeal.AppealService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;

@Service
@AllArgsConstructor
public class AppealServiceImpl implements AppealService {

    private AppealRepository appealRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Appeal appeal) {
        appealRepository.save(appeal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        appealRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Appeal findById(Long id) {
        return appealRepository.findById(id).orElse(null);
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<Appeal> findAll() {
        return appealRepository.findAll();
    }

    @Override
    public void moveToArchive(Long id, ZonedDateTime zonedDateTime) {
        appealRepository.moveToArchive(id, ZonedDateTime.now());
    }

    @Override
    @Transactional(readOnly = true, rollbackFor=Exception.class)
    public Appeal findByIdNotArchived(Long id) {
        return appealRepository.findByIdNotArchived(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor=Exception.class)
    public Collection<Appeal> findAllByIdNotArchived(Collection<Long> id) {
        return appealRepository.findAllByIdNotArchived(id);
    }
}