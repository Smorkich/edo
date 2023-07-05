package com.education.service.question.impl;

import com.education.feign.QuestionFeignClient;
import com.education.service.question.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.QuestionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;

/**
 * @author Nadezhda Pupina
 * Service в "edo-service", создает связь контроллера и RestTemplate
 */
@Service
@AllArgsConstructor
@Log4j2
public class QuestionServiceImpl implements QuestionService {

    private final QuestionFeignClient questionFeignClient;

    @Override
    public QuestionDto save(QuestionDto questionDto) {
        return questionFeignClient.save(questionDto);
    }

    @Override
    public void delete(long id) {
        questionFeignClient.delete(id);
    }

    @Override
    public QuestionDto findById(long id) {
        return questionFeignClient.findById(id);
    }

    @Override
    public Collection<QuestionDto> findAll() {
        return questionFeignClient.findAll();

    }

    @Override
    public Collection<QuestionDto> findAllById(Iterable<Long> ids) {
        return questionFeignClient.findAllById(ids);
    }

    @Override
    public void moveToArchived(Long id) {
        questionFeignClient.moveToArchived(id);
    }

    @Override
    public QuestionDto findByIdNotArchived(Long id) {
        return questionFeignClient.getFileNotArchived(id);
    }

    @Override
    public Collection<QuestionDto> findByAllIdNotArchived(String ids) {
        return questionFeignClient.getFilesNotArchived(ids);
    }
}
