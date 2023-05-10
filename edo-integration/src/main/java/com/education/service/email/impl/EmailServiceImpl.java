package com.education.service.email.impl;

import com.education.service.email.EmailService;
import com.education.service.email.SendEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class EmailServiceImpl implements EmailService {

    private final SendEmailService sendEmailService;

    @Override
    public void createEmail(List<String> emails, String appealURL, String appealNumber) {
        String subject = "Новое обращение: " + appealNumber;
        String message = "Создано новое обращение с номером " +
                appealNumber + ". Ссылка на обращение: " +
                appealURL;
        for (String email : emails) {
            sendEmailService.sendEmail(email, subject, message);
        }
    }

    /**
     * Создает текст письма при создании резолюции и отправляет в SendEmailService
     */
    @Override
    public void createMailWhenCreateResolution(List<String> emailsExecutors, List<String> fioExecutors,
                                               String emailSigner, String fioSigner,
                                               String emailCurator, String fioCurator,
                                               String appealURL, String appealNumber) {

        String subject = "Новая резолюция";
        String signerPost = "подписантом";
        String executorPost = "исполнителем";
        String curatorPost = "куратором";
        String endMessage = " резолюции в обращении с номером " +
                appealNumber + ". Ссылка на обращение: " +
                appealURL;
        //для Executors
        for (var i = 0; i < emailsExecutors.size(); i++) {
            sendEmailService.sendEmail(emailsExecutors.get(i), subject,
                    createMessageForMailWhenCreateResolution(fioExecutors.get(i), executorPost, endMessage));
        }
        //для Signer
        sendEmailService.sendEmail(emailSigner, subject,
                createMessageForMailWhenCreateResolution(fioSigner, signerPost, endMessage));

        //для Curator
        sendEmailService.sendEmail(emailCurator, subject,
                createMessageForMailWhenCreateResolution(fioCurator, curatorPost, endMessage));
    }

    /**
     * Собирает текст письма для отправки в SendEmailService
     */
    private String createMessageForMailWhenCreateResolution(String fio, String post,
                                                            String endMessage) {
        return "Добрый день, " + fio + "!" +
                " Вы являетесь " + post +
                endMessage;
    }
}
