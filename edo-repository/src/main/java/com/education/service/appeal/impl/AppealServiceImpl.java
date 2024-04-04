package com.education.service.appeal.impl;

import com.education.entity.Appeal;
import com.education.entity.Employee;
import com.education.projection.ResolutionProjectionForAppealFile;
import com.education.repository.appeal.AppealRepository;
import com.education.service.appeal.AppealService;
import com.education.service.deadlineResolution.DeadlineResolutionService;
import com.education.service.execution.ExecutorReportService;
import com.education.service.resolution.ResolutionService;
import lombok.AllArgsConstructor;
import model.dto.AppealFileDto;
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
    private ResolutionService resolutionService;
    private DeadlineResolutionService deadlineResolutionService;
    private ExecutorReportService executorReportService;

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
    public Collection<AppealFileDto> findAllForAppealFileById(Long appealId) {
        var appealResolutions =  resolutionService.findAllByAppealId(appealId);
        var appealResolutionsId = appealResolutions.stream().map(ResolutionProjectionForAppealFile::getId).toList();
        var resolutionExecutorReports = executorReportService.getResolutionStatuses(appealResolutionsId);
        var resolutionDeadlines = deadlineResolutionService.findLastDeadlinesByResolutionsId(appealResolutionsId);
        return appealResolutions.stream()
                .map(res -> AppealFileDto.builder()
                        .id(res.getId())
                        .creationDate(res.getCreationDate())
                        .executorFIOs(res.getExecutor().stream().map(Employee::getFioNominative).toList())
                        .resolutionStatus(resolutionExecutorReports.get(res.getId()) != null ? "Исполнена" : "Не исполнена")
                        .deadlineResolution(resolutionDeadlines.get(res.getId()))
                        .build()).toList();
    }

}