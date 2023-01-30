package com.education.service.appeal.impl;

import com.education.service.appeal.AppealService;
import com.education.service.author.AuthorService;
import com.education.service.filePool.FilePoolService;
import com.education.service.question.QuestionService;
import lombok.AllArgsConstructor;
import model.dto.AppealDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import static model.enum_.Status.NEW_STATUS;

/**
 * Сервис-слой для Appeal
 */
@Service
@AllArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final RestTemplate restTemplate;

    private final AuthorService authorService;
    private final QuestionService questionService;
    private final FilePoolService filePoolService;
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
     * Изменение обращения, добавление status,маппинг  Authors,Questions
     */
    public AppealDto save(AppealDto appealDto) {

        appealDto.setAuthors(appealDto.getAuthors().stream()
                .map(authorService::save)
                .collect(Collectors.toList()));
        appealDto.setAppealsStatus(NEW_STATUS);
        appealDto.setCreationDate(ZonedDateTime.now());

        appealDto.setQuestions(appealDto.getQuestions().stream()
                .map(questionService::save)
                .collect(Collectors.toList()));

        appealDto.setFile(appealDto.getFile().stream()
                .map(filePoolService::save)
                .collect(Collectors.toList()));

        return restTemplate.postForObject(URL, appealDto, AppealDto.class);
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
        var appealDto = findById(id);
        restTemplate.put(URL + "/move/" + id, appealDto, AppealDto.class);
    }

    /**
     * Нахождение обращения по id не из архива
     */
    @Override
    public AppealDto findByIdNotArchived(Long id) {
        return restTemplate.getForObject(URL + "/findByIdNotArchived/" + id, AppealDto.class);
    }

    /**
     * Нахождение всех обращений не из архива
     */
    @Override
    public Collection<AppealDto> findAllNotArchived() {
        return restTemplate.getForObject(URL + "/findAllNotArchived", Collection.class);
    }
}
