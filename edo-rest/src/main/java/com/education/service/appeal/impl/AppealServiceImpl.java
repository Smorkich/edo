package com.education.service.appeal.impl;

import com.education.service.appeal.AppealService;
import com.education.service.author.AuthorService;
import com.education.service.filepool.FilePoolService;
import com.education.service.question.QuestionService;
import lombok.AllArgsConstructor;
import model.dto.AppealDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

import static model.enums.Status.NEW_STATUS;

/**
 * Service в "edo-rest", служит для связи контроллера и RestTemplate
 */
@Service
@AllArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final RestTemplate restTemplate;
    private final AuthorService authorService;
    private final QuestionService questionService;
    private final FilePoolService filePoolService;

    /**
     * Метод сохранения нового обращения в БД
     */
    public AppealDto save(AppealDto appealDto) {

        /**
         * Сохранение новых авторов и маппинг чтобы у авторов были id из таблицы
         */
        appealDto.setAuthors(appealDto.getAuthors().stream()
                .map(authorDto -> authorService.save(authorDto))
                .collect(Collectors.toList()));
        appealDto.setAppealsStatus(NEW_STATUS);
        appealDto.setCreationDate(ZonedDateTime.now());

        appealDto.setQuestions(appealDto.getQuestions().stream()
                .map(questionDto -> questionService.save(questionDto))
                .collect(Collectors.toList()));

        appealDto.setFile(appealDto.getFile().stream()
                .map(filePoolDto -> filePoolService.save(filePoolDto))
                .collect(Collectors.toList()));

        return restTemplate.postForObject("http://edo-repository/api/repository/appeal", appealDto, AppealDto.class);
    }
}
