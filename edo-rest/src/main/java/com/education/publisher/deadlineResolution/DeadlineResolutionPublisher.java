package com.education.publisher.deadlineResolution;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.DeadlineResolutionDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static model.constant.Constant.DEADLINE_CHANGE_QUEUE;

@Component
@AllArgsConstructor
@Log4j2
public class DeadlineResolutionPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Принимает объект DeadlineResolutionDto и помещает его в очередь DEADLINE_CHANGE_QUEUE
     *
     * @param deadlineResolutionDto - объект DeadlineResolutionDto, который будет помещен в очередь
     */
    public void reasonForTransferDeadline(DeadlineResolutionDto deadlineResolutionDto) {
        log.info("Publish in queue to change deadline \"DEADLINE_CHANGE_QUEUE\"  {}", deadlineResolutionDto);
        rabbitTemplate.convertAndSend(DEADLINE_CHANGE_QUEUE, deadlineResolutionDto);
        log.info("Added in queue to change deadline \"DEADLINE_CHANGE_QUEUE\" {}", deadlineResolutionDto);
    }
}

