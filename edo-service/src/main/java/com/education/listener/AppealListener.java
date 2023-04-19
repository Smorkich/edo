package com.education.listener;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static model.constant.Constant.REST_TO_SERVICE_APPEAL_QUEUE;

/**
 * Слушатель для очередей с AppealDto
 */
@Component
@AllArgsConstructor
@Log4j2
public class AppealListener {
    /**
     * Подписывается на очередь REST_TO_SERVICE_APPEAL_QUEUE, получает из нее сообщение
     *
     * @param message - сообщение из очереди о том, какой работник какое обращение прочитал
     */
    @RabbitListener(queues = REST_TO_SERVICE_APPEAL_QUEUE)
    public void receiveEmployeeReadAppealMessage(String message) {
        log.info("Received from the queue \"REST_TO_SERVICE_APPEAL_QUEUE\" {}", message);
    }
}
