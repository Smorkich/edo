package com.education.service.resolution.impl;

import com.education.service.appeal.AppealService;


import com.education.service.emloyee.EmployeeService;
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
    private final EmployeeService employeeService;
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

        //получение Email и ФИО Executors
        List<String> emailsExecutors = new ArrayList<>(getEmployeesEmails(resolutionDto.getExecutor()));
        List<String> fioExecutors = new ArrayList<>(getEmployeesFIO(resolutionDto.getExecutor()));

        //получение Appeal.number и Appeal.Id
        var questionId = resolutionDto.getQuestion().getId();
        var appealNumber = appealService.findAppealByQuestionsId(questionId).getNumber();
        var appealId = appealService.findAppealByQuestionsId(questionId).getId();

        //получение AppealUrl
        var builderForAppealUrl = buildURI(EDO_REPOSITORY_NAME, APPEAL_URL + "/" + appealId);

        //заполнение мапы данными
        valueMapForSendingObjects.add("appealURL", builderForAppealUrl.toString());
        valueMapForSendingObjects.add("appealNumber", appealNumber);
        valueMapForSendingObjects.addAll("emailsExecutors", emailsExecutors);
        valueMapForSendingObjects.addAll("fioExecutors", fioExecutors);
        valueMapForSendingObjects.add("emailSigner", getEmployeeEmail(resolutionDto.getSigner()));
        valueMapForSendingObjects.add("fioSigner", getEmployeeFIO(resolutionDto.getSigner()));
        valueMapForSendingObjects.add("emailCurator", getEmployeeEmail(resolutionDto.getCurator()));
        valueMapForSendingObjects.add("fioCurator", getEmployeeFIO(resolutionDto.getCurator()));

        HttpHeaders headers = new HttpHeaders();
        var requestEntity = new HttpEntity<>(valueMapForSendingObjects, headers);

        //отправка мапы на edo-integration
        restTemplate.postForEntity(builder.toString(), requestEntity, Object.class);
    }

    /**
     * Метод достает emails из коллекции EmployeeDto
     */
    private Collection<String> getEmployeesEmails(Collection<EmployeeDto> employees) {
        List<String> result = new ArrayList<>();
        if (employees != null) {
            for (EmployeeDto emp : employees) {
                result.add(getEmployeeEmail(emp));
            }
        }
        return result;
    }

    /**
     * Метод достает ФИО из коллекции EmployeeDto
     */
    private Collection<String> getEmployeesFIO(Collection<EmployeeDto> employees) {
        List<String> result = new ArrayList<>();
        if (employees != null) {
            for (EmployeeDto emp : employees) {
                result.add(getEmployeeFIO(emp));
            }
        }
        return result;
    }

    private String getEmployeeEmail(EmployeeDto emp) {
        return employeeService.findById(emp.getId()).getEmail();
    }

    private String getEmployeeFIO(EmployeeDto emp) {
        return employeeService.findById(emp.getId()).getFioNominative();
    }
}
