package com.education.controller.approval;

import com.education.service.approval.ApprovalService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/service/appeal")
@ApiOperation("Rest-контроллер для ApprovalDto, который отправляет запросы от клиентов к сервисам edo-service")
public class ApprovalRestController {
    private final ApprovalService approvalService;
}
