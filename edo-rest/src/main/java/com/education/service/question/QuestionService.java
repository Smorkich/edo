package com.education.service.question;

import model.dto.QuestionDto;

public interface QuestionService {

    /**
     * Метод сохранения нового обращения в БД
     */
    QuestionDto save(QuestionDto questionDto);
}
