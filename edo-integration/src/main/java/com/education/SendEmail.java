package com.education;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {

    private MailSender mailSender;

    public void sendMail() {

        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo("noreply.ros.edo@gmail.com");
        msg.setFrom("noreply.ros.edo@gmail.com");
        msg.setSubject("Автоматический ответ на Ваше обращение");
        msg.setText("Здравствуйте! Ваше обращение зарегистрировано и будет обработано в течении 3-х рабочих дней.");

        mailSender.send(msg);
    }
}
