package com.education.publisher.recognition.impl;

import com.education.publisher.recognition.RecognitionPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.RecognitionDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static model.constant.Constant.FILE_RECOGNITION_START;

/**
 * Писатель для очередей с RecognitionDto
 */
@Component
@AllArgsConstructor
@Log4j2
public class RecognitionPublisherImpl implements RecognitionPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Принимает объект RecognitionDto и помещает его в очередь FILE_RECOGNITION_START
     *
     * @param recognitionDto - объект RecognitionDto, который будет помещен в очередь
     */
    public void recognize(RecognitionDto recognitionDto) {
        log.info("Publish to approvalQueue \"FILE_RECOGNITION_START\" {}", recognitionDto);
        rabbitTemplate.convertAndSend(FILE_RECOGNITION_START, recognitionDto);
        log.info("Added to approvalQueue \"FILE_RECOGNITION_START\" {}", recognitionDto);

    }

}
