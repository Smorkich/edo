package com.education.listener;

import com.education.service.nomenclature.NomenclatureService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.NomenclatureDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static model.constant.Constant.REST_TO_SERVICE_NOMENCLATURE_QUEUE;

@Component
@AllArgsConstructor
@Log4j2
public class NomenclatureListener {

    private final NomenclatureService nomenclatureService;

    /**
     * Подписывается на очередь REST_TO_SERVICE_NOMENCLATURE_QUEUE, получает из нее объект NomenclatureDto
     * @param nomenclatureDto - объект nomenclatureDto, полученный из очереди
     */
    @RabbitListener(queues = REST_TO_SERVICE_NOMENCLATURE_QUEUE)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public void receive(NomenclatureDto nomenclatureDto) {
        log.info("Received from the queue \"REST_TO_SERVICE_NOMENCLATURE_QUEUE\" {}", nomenclatureDto);
        var currentValue = nomenclatureDto.getCurrentValue();
        nomenclatureDto.setCurrentValue(++currentValue);
        nomenclatureService.save(nomenclatureDto);
    }

}
