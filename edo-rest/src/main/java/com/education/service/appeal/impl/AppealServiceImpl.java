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

import static model.enum_.Status.NEW_STATUS;

/**
 * Service в "edo-rest", служит для связи контроллера и RestTemplate
 */
@Service
@AllArgsConstructor
public class AppealServiceImpl implements AppealService {

    private static final String URL = "http://edo-service/api/service/appeal";
    private final RestTemplate restTemplate;


    @Override
    public AppealDto save(AppealDto appealDto) {
        return restTemplate.postForObject(URL, appealDto, AppealDto.class);
    }
    @Override
    public void moveToArchive(Long id) {
        restTemplate.put(URL + "/move/" + id, AppealDto.class);
    }

    @Override
    public AppealDto findById(Long id) {
        return restTemplate.getForObject(URL + "/" + id, AppealDto.class);
    }
}
