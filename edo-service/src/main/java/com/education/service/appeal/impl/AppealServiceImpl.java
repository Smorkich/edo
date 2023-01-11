package com.education.service.appeal.impl;

import com.education.service.appeal.AppealService;
import lombok.AllArgsConstructor;
import model.dto.AppealDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Collection;

/**
 * Сервис-слой для Appeal
 */
@Service
@AllArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final RestTemplate restTemplate;
    private final String URL = "http://edo-repository/api/repository/appeal";

    /**
     * Нахождение обращения по id
     */
    @Override
    public AppealDto findById(Long id) {
        return restTemplate.getForObject(URL + "/" + id, AppealDto.class);
    }

    /**
     * Нахождение всех обращений
     */
    @Override
    public Collection<AppealDto> findAll() {
        return restTemplate.getForObject(URL, Collection.class);
    }

    /**
     * Сохранение обращения
     */
    @Override
    public void save(AppealDto appealDto) {
        restTemplate.postForObject(URL, appealDto, AppealDto.class);
    }

    /**
     * Удаления обращения по Id
     */
    @Override
    public void delete(Long id) {
        restTemplate.delete(URL + "/" + id, AppealDto.class);
    }

    /**
     * Перенос обращения в архив по id
     */
    @Override
    public void moveToArchive(Long id) {
        AppealDto appeal = findById(id);
        appeal.setArchivedDate(ZonedDateTime.now());
        restTemplate.postForObject(URL + "/move/"+ id, appeal, AppealDto.class);
    }

    /**
     * Нахождение обращения по id не из архива
     */
    @Override
    public AppealDto findByIdNotArchived(Long id) {
        return restTemplate.getForObject(URL + "/findByIdNotArchived/"+ id, AppealDto.class);
    }

    /**
     * Нахождение всех обращений не из архива
     */
    @Override
    public Collection<AppealDto> findAllNotArchived() {
        return restTemplate.getForObject(URL + "/findAllNotArchived", Collection.class);
    }
}
