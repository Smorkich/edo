package com.education.service.question.impl;

import com.education.service.question.QuestionService;
import lombok.AllArgsConstructor;
import model.dto.QuestionDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final RestTemplate restTemplate;
    private static final String URL = "http://edo-repository/api/repository/question";

    @Override
    public QuestionDto save(QuestionDto questionDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return  restTemplate.exchange(URL, HttpMethod.POST, new HttpEntity<>(questionDto, headers), QuestionDto.class).getBody();
    }
}
