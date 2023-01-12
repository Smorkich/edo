package com.education.mapper;

import com.education.entity.Question;
import model.dto.QuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Nadezhda Pupina
 * Интерфейс для реализации конвертации вопроса в ДТО и обратно
 */

@Mapper(componentModel = "spring")
public interface QuestionMapper extends AbstractMapper<Question, QuestionDto> {
    QuestionMapper QUESTION_MAPPER = Mappers.getMapper(QuestionMapper.class);

}