package com.education.service.question.impl;

import com.education.service.question.QuestionService;
import lombok.AllArgsConstructor;
import model.dto.QuestionDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final RestTemplate restTemplate;

    @Override
    public QuestionDto save(QuestionDto questionDto) {
        return restTemplate.postForObject("http://edo-repository/api/repository/question", questionDto, QuestionDto.class);
    }
}
