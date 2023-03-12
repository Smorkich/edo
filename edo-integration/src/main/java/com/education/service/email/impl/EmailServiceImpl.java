package com.education.service.email.impl;

import com.education.service.email.EmailService;
import com.education.service.email.SendEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
public class EmailServiceImpl implements EmailService {

    private final SendEmailService sendEmailService;
    @Override
    public void createEmail(Set<String> emails, String appealURL, String appealNumber) {
        String subject = "Новое обращение: " + appealNumber;
        StringBuilder message = new StringBuilder("Создано новое обращение с номером ");
        message.append(appealNumber).append(". Ссылка на обращение: ").append(appealURL);
        for (String email : emails) {
                sendEmailService.sendEmail(email, subject, message.toString());
        }
    }
}
