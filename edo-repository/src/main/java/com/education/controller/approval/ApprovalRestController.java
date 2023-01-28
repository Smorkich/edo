package com.education.controller.approval;

import com.education.service.approval.ApprovalService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.education.mapper.ApprovalMapper.APPROVAL_MAPPER;

/**
 * Rest-контроллер сущности Approval для отправки запросов от клиентов к БД
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/repository/approval")
public class ApprovalRestController {
    private final ApprovalService approvalService;
}
