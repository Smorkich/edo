package com.education.service.appeal.impl;

import com.education.service.appeal.AppealService;
import com.education.service.author.AuthorService;
import com.education.service.filepool.FilePoolService;
import com.education.service.question.QuestionService;
import lombok.AllArgsConstructor;
import model.dto.AppealDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

import static model.enum_.Status.NEW_STATUS;

/**
 * Service в "edo-rest", служит для связи контроллера и RestTemplate
 */
@Service
@AllArgsConstructor
public class AppealServiceImpl implements AppealService {

    private static final String URL = "http://edo-repository/api/repository/appeal";
    private final RestTemplate restTemplate;
    private final AuthorService authorService;
    private final QuestionService questionService;
    private final FilePoolService filePoolService;

    /**
     * Метод сохранения нового обращения в БД
     */
    @Override
    public AppealDto save(AppealDto appealDto) {

        //Сохранение новых авторов и маппинг чтобы у авторов были id из таблицы
        appealDto.setAppealsStatus(NEW_STATUS);
        appealDto.setCreationDate(ZonedDateTime.now());

        appealDto.setAuthors(appealDto.getAuthors().stream()
                .map(authorService::save)
                .collect(Collectors.toList()));

        appealDto.setQuestions(appealDto.getQuestions().stream()
                .map(questionService::save)
                .collect(Collectors.toList()));

        appealDto.setFile(appealDto.getFile().stream()
                .map(filePoolService::save)
                .collect(Collectors.toList()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(URL, HttpMethod.POST, new HttpEntity<>(appealDto, headers), AppealDto.class).getBody();
    }
}
