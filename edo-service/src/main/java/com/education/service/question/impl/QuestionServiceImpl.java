package com.education.service.question.impl;

import com.education.service.question.QuestionService;
import lombok.AllArgsConstructor;
import model.dto.DepartmentDto;
import model.dto.QuestionDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

/**
 * @author Nadezhda Pupina
 * Service в "edo-service", создает связь контроллера и RestTemplate
 */
@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final String URL = "http://edo-repository/api/repository/question";
    private final RestTemplate restTemplate;

    @Override
    public void save(QuestionDto questionDto) {
        restTemplate.postForObject(URL, questionDto, QuestionDto.class);
    }

    @Override
    public void delete(long id) {
        restTemplate.delete(URL + "/" + id, QuestionDto.class);
    }

    @Override
    public String findById(long id) {
        return restTemplate.getForObject(URL + "/" + id, String.class);
    }

    @Override
    public Collection<QuestionDto> findByAllId(String ids) {
        return restTemplate.getForObject(URL + "/all/" + ids, List.class);
    }

    @Override
    public void moveToArchived(Long id) {
        restTemplate.postForObject(URL + "/" + id, null, String.class);
    }

    @Override
    public QuestionDto findByIdNotArchived(Long id) {
        return restTemplate.getForObject(URL + "/notArchived/" + id, QuestionDto.class);
    }

    @Override
    public Collection<QuestionDto> findByAllIdNotArchived(String ids) {
        return restTemplate.getForObject(URL + "/notArchivedAll/" + ids, List.class);
    }
}
