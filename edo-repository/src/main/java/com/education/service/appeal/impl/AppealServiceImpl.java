package com.education.service.appeal.impl;

import com.education.entity.Appeal;
import com.education.repository.appeal.AppealRepository;
import com.education.service.appeal.AppealService;
import lombok.AllArgsConstructor;
import model.enum_.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Сервис-слой для сущности Appeal
 */
@Service
@AllArgsConstructor
public class AppealServiceImpl implements AppealService {

    private AppealRepository appealRepository;

    /**
     * Сохранение нового сообщения
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Appeal save(Appeal appeal) {
        return appealRepository.saveAndFlush(appeal);
    }

    /**
     * Удаление обращения
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        appealRepository.deleteById(id);
    }

    /**
     * Нахождение обращения по id
     */
    @Override
    @Transactional(readOnly = true)
    public Appeal findById(Long id) {
        return appealRepository.findById(id).orElse(null);
    }

    /**
     * Нахождение всех обращений
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Appeal> findAll() {
        return appealRepository.findAll();
    }

    /**
     * Перенос в архив обращения и изменение статуса на (ARCHIVE)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveToArchive(Long id) {
        appealRepository.findByIdAndArchivedDateIsNull(id).ifPresent(entity -> {
            entity.setAppealsStatus(Status.ARCHIVE);
            entity.setArchivedDate(ZonedDateTime.now());
        });
    }

    /**
     * Нахождение обращения по id не из архива
     */
    @Override
    @Transactional(readOnly = true)
    public Appeal findByIdNotArchived(Long id) {
        return appealRepository.findByIdNotArchived(id).orElse(null);
    }

    /**
     * Нахождение всех обращений не из архива
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Appeal> findAllNotArchived() {
        return appealRepository.findAllNotArchived();
    }

    /**
     * Нахождение обращения по Questions id
     */
    @Override
    @Transactional(readOnly = true)
    public Appeal findAppealByQuestionsId(Long id) {
        return appealRepository.findAppealByQuestionsId(id).orElseThrow(() -> new NoSuchElementException("Вопросы обращения с id " + id + " не найдены"));
    }

    /**
     * Изменяет статус строки Appeal в базе данных на REGISTERED по id обращения, переданному в параметре
     *
     * @param id идентификатор регистрируемого Appeal
     * @return AppealDto - DTO сущности Appeal (обращение)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Appeal register(Long id) {
        Appeal appeal = appealRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Обращение с id + " + id + " не найдено"));
        appeal.setAppealsStatus(Status.REGISTERED);
        return appeal;
    }

    @Override
    @Transactional(readOnly = true)
    public Long isLastAppealResolutionArchived(Long resolutionId) {
        return appealRepository.isLastAppealResolutionArchived(resolutionId).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setAppealStatusIfLastResolutionArchived(Long id) {
        appealRepository.setAppealStatusIfLastResolutionArchived(id);
    }

}