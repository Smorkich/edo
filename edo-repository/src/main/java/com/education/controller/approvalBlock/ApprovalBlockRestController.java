package com.education.controller.approvalBlock;

import com.education.service.approvalBlock.ApprovalBlockService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest-контроллер сущности Member для отправки запросов от клиентов к БД
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/repository/approvalBlock")
public class ApprovalBlockRestController {
    private final ApprovalBlockService approvalBlockService;
}
