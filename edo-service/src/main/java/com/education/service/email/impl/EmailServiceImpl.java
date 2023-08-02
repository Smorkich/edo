package com.education.service.email.impl;

import com.education.service.appeal.AppealService;
import com.education.service.email.EmailService;
import com.education.service.emloyee.EmployeeService;
import lombok.AllArgsConstructor;
import model.dto.EmployeeDto;
import model.dto.ResolutionDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;
import static model.constant.Constant.APPEAL_URL;


/**
 * @author Kirill Vasiljev
 * Сервис-слой для Email (метод sendMessage для Resolution)
 */


@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final RestTemplate restTemplate;
    private final AppealService appealService;
    private final EmployeeService employeeService;


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
        employees.stream()
                .filter(Objects::nonNull)
                .forEach(x -> result.add(getEmployeeEmail(x)));
        return result;
    }

    /**
     * Метод достает ФИО из коллекции EmployeeDto
     */
    private Collection<String> getEmployeesFIO(Collection<EmployeeDto> employees) {
        List<String> result = new ArrayList<>();
        employees.stream()
                .filter(Objects::nonNull)
                .forEach(x -> result.add(getEmployeeFIO(x)));
        return result;
    }

    private String getEmployeeEmail(EmployeeDto emp) {
        return employeeService.findById(emp.getId()).getEmail();
    }

    private String getEmployeeFIO(EmployeeDto emp) {
        return employeeService.findById(emp.getId()).getFioNominative();
    }
}
