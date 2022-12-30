package com.education.service.question.impl;

import com.education.entity.Question;
import com.education.repository.question.QuestionRepository;
import com.education.service.question.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    QuestionRepository questionRepository;

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }
}
