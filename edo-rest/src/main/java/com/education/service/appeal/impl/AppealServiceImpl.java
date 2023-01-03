package com.education.service.appeal.impl;

import com.education.service.appeal.AppealService;
import lombok.AllArgsConstructor;
import model.dto.AppealDto;
import model.dto.AuthorDto;
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

    private final String URL_REPOSITORY_APPEAL = "http://edo-repository/api/repository/appeal";
    private final String URL_REPOSITORY_AUTHORS = "http://edo-repository/api/repository/author";
//    private final String URL_REPOSITORY_QUESTION = "http://edo-repository/api/repository/question";
//    private final String URL_REPOSITORY_FILE_POOL = "http://edo-repository/api/repository/file_poll";
    private final RestTemplate restTemplate;

    /**
     * Метод сохранения нового обращения в БД
     */
    public AppealDto save(AppealDto appealDto) {

        /**
         * Сохранение новых авторов и маппинг чтобы у авторов были id из таблицы
         */
        appealDto.setAuthors(appealDto.getAuthors().stream()
                .map(authorDto -> restTemplate.postForObject(URL_REPOSITORY_AUTHORS, authorDto, AuthorDto.class))
                .collect(Collectors.toList()));
        appealDto.setAppealsStatus(NEW_STATUS);
        appealDto.setCreationDate(ZonedDateTime.now());

//        appealDto.setQuestions(appealDto.getQuestions().stream()
//                .map(questionDto -> restTemplate.postForObject(URL_REPOSITORY_QUESTION, questionDto, QuestionDto.class))
//                .collect(Collectors.toList()));

//        appealDto.setFile(appealDto.getFile().stream()
//                .map(filePoolDto -> restTemplate.postForObject(URL_REPOSITORY_FILE_POOL, filePoolDto, FilePoolDto.class))
//                .collect(Collectors.toList()))

        return restTemplate.postForObject(URL_REPOSITORY_APPEAL, appealDto, AppealDto.class);
    }
}
