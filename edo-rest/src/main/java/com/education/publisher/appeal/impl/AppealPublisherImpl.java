package com.education.publisher.appeal.impl;

import com.education.publisher.appeal.AppealPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static model.constant.Constant.REST_TO_SERVICE_APPEAL_QUEUE;

/**
 * Писатель для очередей с Appeal
 */
@Component
@AllArgsConstructor
@Log4j2
public class AppealPublisherImpl implements AppealPublisher {
    private final RabbitTemplate rabbitTemplate;

    /**
     * Принимает сообщение о том, какой работник какое обращение прочитал, и помещает сообщение в очередь REST_TO_SERVICE_APPEAL_QUEUE
     */
    @Override
    public void EmployeeReadAppealMessage(String message) {
        log.info("Publish to appealQueue \"REST_TO_SERVICE_APPEAL_QUEUE\" {}", message);
        rabbitTemplate.convertAndSend(REST_TO_SERVICE_APPEAL_QUEUE, message);
        log.info("Added to appealQueue \"REST_TO_SERVICE_APPEAL_QUEUE\" {}", message);
    }
}
