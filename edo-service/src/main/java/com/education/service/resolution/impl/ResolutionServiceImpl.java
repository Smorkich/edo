package com.education.service.resolution.impl;

import com.education.service.appeal.AppealService;

import com.education.service.resolution.ResolutionService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.constant.Constant;
import model.dto.ResolutionDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import model.dto.AppealDto;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;


import static model.enum_.Status.UNDER_CONSIDERATION;


/**
 * Сервис-слой для Resolution
 */

@AllArgsConstructor
@Service
public class ResolutionServiceImpl implements ResolutionService {

    private RestTemplate restTemplate;

    private final AppealService appealService;
    private final String URL = "http://edo-repository/api/repository/resolution";


    @Override
    public void save(ResolutionDto resolutionDto) {
        HttpHeaders headers = new HttpHeaders();
        resolutionDto.setIsDraft(true);
        resolutionDto.setCreationDate(ZonedDateTime.now());
        resolutionDto.setLastActionDate(ZonedDateTime.now());

        Long questionId = resolutionDto.getQuestion().getId();
        AppealDto appealDto = appealService.findAppealByQuestionsId(questionId);

        // Назначения статуса и времени создания
        appealDto.setAppealsStatus(UNDER_CONSIDERATION);
        appealDto.setCreationDate(ZonedDateTime.now());
        appealService.save(appealDto);




        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = URIBuilderUtil.buildURI(Constant.EDO_REPOSITORY_NAME, "api/repository/resolution/add").toString();

        restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(resolutionDto, headers), ResolutionDto.class).getBody();
    }

    @Override
    public void moveToArchive(Long id) {
        restTemplate.postForObject(URL + "/move/" + id, null, ResolutionDto.class);
    }

    @Override
    public ResolutionDto findById(Long id) {
        return restTemplate.getForObject(URL + "/{id}", ResolutionDto.class);

    }

    @Override
    public Collection<ResolutionDto> findAllById(Long id) {
        return restTemplate.getForObject(URL + "/all/" + id, List.class);
    }

    @Override
    public ResolutionDto findByIdNotArchived(Long id) {

        return restTemplate.getForObject(URL + "/notArchived/" + id, ResolutionDto.class);
    }

    @Override
    public Collection<ResolutionDto> findAllByIdNotArchived(Long id) {
        return restTemplate.getForObject(URL + "/notArchived/all/" + id, Collection.class);
    }
}
