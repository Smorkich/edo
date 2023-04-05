package com.education.service.resolution.impl;

import com.education.service.appeal.AppealService;


import com.education.service.resolution.ResolutionService;

import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.constant.Constant;

import model.dto.EmployeeDto;
import model.dto.ResolutionDto;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import model.dto.AppealDto;

import static com.education.util.URIBuilderUtil.buildURI;

import java.time.ZonedDateTime;
import java.util.*;

import static model.constant.Constant.*;

import static model.enum_.Status.UNDER_CONSIDERATION;


/**
 * Сервис-слой для Resolution
 */

@AllArgsConstructor
@Service
public class ResolutionServiceImpl implements ResolutionService {

    private final AppealService appealService;


    private RestTemplate restTemplate;

    @Override
    public ResolutionDto save(ResolutionDto resolutionDto) {
        if (resolutionDto.getCreationDate() == null) {
            resolutionDto.setCreationDate(ZonedDateTime.now());
        }
        resolutionDto.setIsDraft(true);

        resolutionDto.setLastActionDate(ZonedDateTime.now());

        Long questionId = resolutionDto.getQuestion().getId();
        AppealDto appealDto = appealService.findAppealByQuestionsId(questionId);

        appealDto.setAppealsStatus(UNDER_CONSIDERATION);

        appealService.save(appealDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = URIBuilderUtil.buildURI(Constant.EDO_REPOSITORY_NAME, "api/repository/resolution/add").toString();
        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(resolutionDto, headers), ResolutionDto.class).getBody();
    }

    @Override
    public void moveToArchive(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL).setPath("/move/").setPath(String.valueOf(id));
        restTemplate.postForObject(builder.toString(), null, ResolutionDto.class);
    }

    @Override
    public ResolutionDto findById(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL).setPath("/").setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), ResolutionDto.class);
    }

    @Override
    public Collection<ResolutionDto> findAllById(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL).setPath("/all/").setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), List.class);
    }

    @Override
    public ResolutionDto findByIdNotArchived(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL).setPath("/notArchived/").setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), ResolutionDto.class);
    }

    @Override
    public Collection<ResolutionDto> findAllByIdNotArchived(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL).setPath("/notArchived/all/").setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), Collection.class);
    }

    /**
     * По принятой резолюции формирует valueMapForSendingObjects для отправки на
     * EDO_INTEGRATION для последующей рассылки сообщений
     */
    @Override
    public void sendMessage(ResolutionDto resolutionDto) {
        var builder = buildURI(EDO_INTEGRATION_NAME, MESSAGE_URL + "/resolution");
        var valueMapForSendingObjects = new LinkedMultiValueMap<>();

        List<String> emailsExecutors = new ArrayList<>();
        List<String> fioExecutors = new ArrayList<>();

        //получение Email и ФИО Executors
        addEmployeesEmailsAndFIO(emailsExecutors, fioExecutors, resolutionDto.getExecutor());

        //получение Appeal.number
        var questionId = resolutionDto.getQuestion().getId();
        var appealNumber = appealService.findAppealByQuestionsId(questionId).getNumber();

        //получение AppealUrl
        var builderForAppealUrl = buildURI(EDO_REPOSITORY_NAME, APPEAL_URL + "/" + appealNumber);

        //заполнение мапы данными
        valueMapForSendingObjects.add("appealURL", builderForAppealUrl.toString());
        valueMapForSendingObjects.add("appealNumber", appealNumber);
        valueMapForSendingObjects.addAll("emailsExecutors", emailsExecutors);
        valueMapForSendingObjects.addAll("fioExecutors", fioExecutors);
        valueMapForSendingObjects.add("emailSigner", resolutionDto.getSigner().getEmail());
        valueMapForSendingObjects.add("fioSigner", resolutionDto.getSigner().getFioNominative());
        valueMapForSendingObjects.add("emailCurator", resolutionDto.getCurator().getEmail());
        valueMapForSendingObjects.add("fioCurator", resolutionDto.getCurator().getFioNominative());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var requestEntity = new HttpEntity<>(valueMapForSendingObjects, headers);

        //отправка мапы на edo-integration
        restTemplate.postForEntity(builder.toString(), requestEntity, Object.class);
    }

    /**
     * Метод достает emails и ФИО в им.п. из коллекции EmployeeDto
     */
    private void addEmployeesEmailsAndFIO(Collection<String> emails, Collection<String> fio, Collection<EmployeeDto> employees) {
        if (employees != null) {
            for (EmployeeDto emp : employees) {
                emails.add(emp.getEmail());
                fio.add(emp.getFioNominative());
            }
        }
    }
}
