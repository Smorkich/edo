package com.education.service.email.impl;

import com.education.service.email.EmailService;
import com.education.service.email.SendEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import model.dto.EmployeeDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;

@Service
@AllArgsConstructor
@Log4j2
public class EmailServiceImpl implements EmailService {

    private final SendEmailService sendEmailService;

    private final RestTemplate restTemplate;

    @Override
    public void createEmail(AppealDto appealDto) {
        Collection<EmployeeDto> collection = appealDto.getAddressee();
        List<Long> ids = new LinkedList<>();
        if (collection != null) {
            for (EmployeeDto emp : collection) {
                ids.add(emp.getId());
            }
        }

        collection = appealDto.getSigner();
        if (collection != null) {
            for (EmployeeDto emp : collection) {
                ids.add(emp.getId());
            }
        }

        EmployeeDto employee;

        var builderForAppealUrl = buildURI(EDO_REPOSITORY_NAME, APPEAL_URL + "/" + appealDto.getId());

        String subject = "Новое обращение: " + appealDto.getNumber();
        StringBuilder message = new StringBuilder("Создано новое обращение с номером ");
        message.append(appealDto.getNumber()).append(". Ссылка на обращение: ").append(builderForAppealUrl.toString());

        for (Long id : ids) {
            var builder = buildURI(EDO_REPOSITORY_NAME, EMPLOYEE_URL + "/" + id);
            employee = restTemplate.getForObject(builder.toString(), EmployeeDto.class);
            if (employee.getEmail() == null) {
                log.warn("Employer with name " + employee.getFioNominative() + " and id " + employee.getId() + " does not have an email");
            } else {
                sendEmailService.sendEmail(employee.getEmail(), subject, message.toString());
            }
        }
    }
}
