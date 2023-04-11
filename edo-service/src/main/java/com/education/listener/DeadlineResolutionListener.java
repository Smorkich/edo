package com.education.listener;

import com.education.service.deadlineResolution.DeadlineResolutionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.DeadlineResolutionDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static model.constant.Constant.DEADLINE_CHANGE_QUEUE;

/**
 * Сервис-слой для Resolution
 */
@Component
@AllArgsConstructor
@Log4j2
public class DeadlineResolutionListener {

    private final DeadlineResolutionService deadlineResolutionService;

    /**
     * Подписывается на очередь DEADLINE_CHANGE_QUEUE, получает из нее объект ResolutionDto и передает
     * в ResolutionService в метод reasonForTransferDeadline
     *
     * @param deadlineResolutionDto - объект DeadlineResolutionDto, полученный из очереди
     */
    @RabbitListener(queues = DEADLINE_CHANGE_QUEUE)
    public void receive(DeadlineResolutionDto deadlineResolutionDto) {
        log.info("Received from the queue \"DEADLINE_CHANGE_QUEUE\" for id {}", deadlineResolutionDto.getId());
        deadlineResolutionService.reasonForTransferDeadline(deadlineResolutionDto);
    }
}
