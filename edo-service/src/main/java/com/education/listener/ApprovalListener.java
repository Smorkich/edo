package com.education.listener;

import com.education.service.approval.ApprovalService;
import lombok.AllArgsConstructor;
import model.dto.ApprovalDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApprovalListener {
    private final ApprovalService approvalService;

    @RabbitListener(queues = "queue")
    public void process(ApprovalDto approvalDto) {
        approvalService.save(approvalDto);
    }
}
