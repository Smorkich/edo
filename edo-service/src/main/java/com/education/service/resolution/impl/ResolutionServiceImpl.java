package com.education.service.resolution.impl;

import com.education.service.appeal.AppealService;

import com.education.service.question.QuestionService;
import com.education.service.resolution.ResolutionService;
import com.education.service.theme.ThemeService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.constant.Constant;
import model.dto.EmployeeDto;
import model.dto.QuestionDto;
import model.dto.ResolutionDto;
import model.dto.ThemeDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.context.Theme;
import org.springframework.web.client.RestTemplate;
import model.dto.AppealDto;

import static com.education.util.URIBuilderUtil.buildURI;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import static model.constant.Constant.*;

import static model.enum_.Status.UNDER_CONSIDERATION;


/**
 * Сервис-слой для Resolution
 */

@AllArgsConstructor
@Service
public class ResolutionServiceImpl implements ResolutionService {

    private final AppealService appealService;
    private final QuestionService questionService;

    private final ThemeService themeService;
    private RestTemplate restTemplate;

    @Override
    public void save(ResolutionDto resolutionDto) {
        HttpHeaders headers = new HttpHeaders();
        resolutionDto.setIsDraft(true);

        resolutionDto.setLastActionDate(ZonedDateTime.now());

        Long questionId = resolutionDto.getQuestion().getId();
        AppealDto appealDto = appealService.findAppealByQuestionsId(questionId);

        // Назначения статуса и времени создания
        appealDto.setAppealsStatus(UNDER_CONSIDERATION);
        appealDto.setCreationDate(ZonedDateTime.now()); // тоже скорее надо будет убрать

        appealService.save(appealDto);
        ThemeDto themeDto = new ThemeDto(1L,"Zanuda",null,null,"444",null);
        themeDto.setCreationDate(ZonedDateTime.now());
        ThemeDto parentThemeDto = new ThemeDto(2L,"Zanuda",null,null,"444",null);
        parentThemeDto.setCreationDate(ZonedDateTime.now());
        themeDto.setParentTheme(null);
//        themeService.save(parentThemeDto);
//        themeService.save(themeDto);
        QuestionDto questionDto = resolutionDto.getQuestion();
        questionDto.setTheme(themeDto);
        questionService.save(questionDto);


        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = URIBuilderUtil.buildURI(Constant.EDO_REPOSITORY_NAME, "api/repository/resolution/add").toString();
        restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(resolutionDto, headers), ResolutionDto.class).getBody();
    }

    @Override
    public void moveToArchive(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/move/")
                .setPath(String.valueOf(id));
        restTemplate.postForObject(builder.toString(), null, ResolutionDto.class);
    }

    @Override
    public ResolutionDto findById(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), ResolutionDto.class);
    }

    @Override
    public Collection<ResolutionDto> findAllById(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/all/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), List.class);
    }

    @Override
    public ResolutionDto findByIdNotArchived(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/notArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), ResolutionDto.class);
    }

    @Override
    public Collection<ResolutionDto> findAllByIdNotArchived(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/notArchived/all/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), Collection.class);
    }
}
