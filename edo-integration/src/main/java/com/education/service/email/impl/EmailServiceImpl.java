package com.education.service.email.impl;

import com.education.service.email.EmailService;
import com.education.service.email.SendEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public void createMailWhenCreateResolution(List<String> emailsExecutors, List<String> fioExecutors,
                                               String emailSigner, String fioSigner,
                                               String emailCurator, String fioCurator,
                                               String appealURL, String appealNumber) {

        String subject = "Новая резолюция";
        StringBuilder endMessage = new StringBuilder(" резолюции в обращении с номером ")
                .append(appealNumber)
                .append(". Ссылка на обращение: ")
                .append(appealURL);

        //для Executors
        for(var i = 0;i<emailsExecutors.size();i++){
        var messageExecutors = new StringBuilder("Добрый день, " + fioExecutors.get(i) + "!")
                .append(" Вы являетесь исполнителем")
                .append(endMessage);
            sendEmailService.sendEmail(emailsExecutors.get(i), subject, messageExecutors.toString());
        }
        //для Signer
        var messageSigner = new StringBuilder("Добрый день, " + fioSigner + "!")
                .append(" Вы являетесь подписантом")
                .append(endMessage);
        sendEmailService.sendEmail(emailSigner, subject, messageSigner.toString());

        //для Curator
        var messageCurator = new StringBuilder("Добрый день, " + fioCurator + "!")
                .append(" Вы являетесь куратором")
                .append(endMessage);
        sendEmailService.sendEmail(emailCurator, subject, messageCurator.toString());
    }
}
