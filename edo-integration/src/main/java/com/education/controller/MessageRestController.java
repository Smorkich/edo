package com.education.controller;

import com.education.service.email.EmailService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AppealDto;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("api/integration/message")
public class MessageRestController {

    private final EmailService emailService;

    @ApiOperation(value = "Отправляет письмо по почте to, с текстом text и заголовком subject")
    @PutMapping("")
    public void createAndSendAppealMessage(@RequestBody AppealDto appealDto) {
        log.info("Creating and sending a message");
        emailService.createEmail(appealDto);
        log.info("Messages were send");
    }

}
