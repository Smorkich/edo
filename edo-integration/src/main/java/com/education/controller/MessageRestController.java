package com.education.controller;

import com.education.service.email.EmailService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("api/integration/message")
public class MessageRestController {

    private final EmailService emailService;

    @ApiOperation(value = "Отправляет письма по почтам emails, с текстом указанием на номер обращения и адрес")
    @PostMapping("")
    public ResponseEntity<Object> createAndSendAppealMessage(@RequestParam("emails") List<String> emails,
                                                             @RequestParam("appealURL") String appealURL,
                                                             @RequestParam("appealNumber") String appealNumber) {
        log.info("Creating and sending a message");
        emailService.createEmail(emails, appealURL, appealNumber);
        log.info("Messages were send");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Отправляет письма по почтам emails куратору, подписанту и исполнителям, " +
            "с текстом указанием на номер обращения и адрес")
    @PostMapping("/resolution")
    public ResponseEntity<Object> createAndSendResolutionMessage(@RequestParam("emailsExecutors") List<String> emailsExecutors,
                                                                 @RequestParam("fioExecutors") List<String> fioExecutors,
                                                                 @RequestParam("emailSigner") String emailSigner,
                                                                 @RequestParam("fioSigner") String fioSigner,
                                                                 @RequestParam("emailCurator") String emailCurator,
                                                                 @RequestParam("fioCurator") String fioCurator,
                                                                 @RequestParam("appealURL") String appealURL,
                                                                 @RequestParam("appealNumber") String appealNumber) {
        log.info("Creating and sending a message");
        emailService.createMailWhenCreateResolution(emailsExecutors, fioExecutors,
                emailSigner, fioSigner,
                emailCurator, fioCurator,
                appealURL, appealNumber);
        log.info("Messages were send");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
