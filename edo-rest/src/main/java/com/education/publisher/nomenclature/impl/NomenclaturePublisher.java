package com.education.publisher.nomenclature.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.NomenclatureDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static model.constant.Constant.REST_TO_SERVICE_NOMENCLATURE_QUEUE;

/**
 * Писатель для очередей с NomenclatureDto
 */
@Component
@AllArgsConstructor
@Log4j2
public class NomenclaturePublisher {
    private final RabbitTemplate rabbitTemplate;

    /**
     * Принимает объект NomenclatureDto и помещает его в очередь REST_TO_SERVICE_NOMENCLATURE_QUEUE
     * @param nomenclatureDto - объект ApprovalDto, который будет помещен в очередь
     */
    public void produce(NomenclatureDto nomenclatureDto) {
        log.info("Publish to nomenclatureQueue \"REST_TO_NOMENCLATURE_QUEUE\" {}", nomenclatureDto);
        rabbitTemplate.convertAndSend(REST_TO_SERVICE_NOMENCLATURE_QUEUE, nomenclatureDto);
        log.info("Added to the nomenclatureQueue \"REST_TO_NOMENCLATURE_QUEUE\" {}", nomenclatureDto);
    }
}
