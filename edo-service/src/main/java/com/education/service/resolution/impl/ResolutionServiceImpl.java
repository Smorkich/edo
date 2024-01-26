package com.education.service.resolution.impl;

import com.education.feign.ResolutionFeignClient;
import com.education.service.appeal.AppealService;
import com.education.service.emloyee.EmployeeService;
import com.education.service.resolution.ResolutionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.dto.AppealDto;
import model.dto.ResolutionDto;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collection;

import static model.enum_.Status.UNDER_CONSIDERATION;


/**
 * Сервис-слой для Resolution
 */

@AllArgsConstructor
@Service
@Slf4j
public class ResolutionServiceImpl implements ResolutionService {

    private final AppealService appealService;
    private final EmployeeService employeeService;
    private final ResolutionFeignClient resolutionFeignClient;

    @Override
    public ResolutionDto save(ResolutionDto resolutionDto) {
        if (resolutionDto.getCreationDate() == null) {
            resolutionDto.setCreationDate(ZonedDateTime.now());
        }
        resolutionDto.setIsDraft(true);
        resolutionDto.setLastActionDate(ZonedDateTime.now());

        Long questionId = resolutionDto.getQuestion().getId();
        AppealDto appealDto = appealService.findAppealByQuestionsId(questionId);
        appealDto.setAppealsStatus(UNDER_CONSIDERATION);
        appealService.save(appealDto);

        return resolutionFeignClient.saveResolution(resolutionDto);
    }

    /**
     * Метод разархивации резолюции
     * Удаляем дату архивации из резолюции
     * Находим обращение по ID резолюции и получаем список всех резолюций для обращения
     * Если резолюция становится единственной для обращения и её статус isDraft = false
     * То меняем статус обращения на UNDER_CONSIDERATION
     */
    @Override
    public void unarchiveResolution(Long id) {
        ResolutionDto resolutionDto = resolutionFeignClient.findById(id);
        resolutionDto.setArchivedDate(null);
        resolutionFeignClient.saveResolution(resolutionDto);
        AppealDto appealDto = appealService.findById(id);
        Collection<ResolutionDto> resolutions = resolutionFeignClient.findAll(appealDto.getId());
        if (resolutions.size() == 1 && !resolutionDto.getIsDraft()) {
            appealDto.setAppealsStatus(UNDER_CONSIDERATION);
            appealService.save(appealDto);
            log.info("Статус обращения id: {} изменен (на расмотрении) ", id);
        }
        log.info("Резолюции id: {} успешно разархивирована", id);
    }

    @Override
    public void moveToArchive(Long id) {
        resolutionFeignClient.moveToArchive(id);
    }

    @Override
    public ResolutionDto findById(Long id) {
        return resolutionFeignClient.findById(id);
    }

    @Override
    public Collection<ResolutionDto> findAllById(Long id) {
        return resolutionFeignClient.findAll(id);
    }

    @Override
    public ResolutionDto findByIdNotArchived(Long id) {
        return resolutionFeignClient.findByIdNotArchived(id);
    }

    @Override
    public Collection<ResolutionDto> findAllByIdNotArchived(Long id) {
        return resolutionFeignClient.findAllByIdNotArchived(id);
    }

}
