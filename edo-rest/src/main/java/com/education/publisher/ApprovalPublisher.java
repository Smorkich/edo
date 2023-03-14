package com.education.publisher;

import lombok.AllArgsConstructor;
import model.dto.ApprovalDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApprovalPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void save(ApprovalDto approvalDto) {
        rabbitTemplate.convertAndSend("queue", approvalDto);
    }
}