package com.education.service.ServiceDeadlineResolution;


import com.education.feign.DeadlineResolutionFeignClient;
import com.education.feign.SendDeadlineNotificationFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.dto.EmailAndIdDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Реализация сервиса для разрешения сроков с использованием Feign клиентов.
 * Этот класс отвечает за получение данных о сотрудниках и отправку уведомлений о сроках.
 *
 * @author Arkadiy_Gumelya
 */

@Slf4j
@Component
@AllArgsConstructor
public class ServiceDeadlineResolutionImpl implements ServiceDeadlineResolution {

    private DeadlineResolutionFeignClient deadlineResolutionFeignClient;
    private SendDeadlineNotificationFeignClient sendDeadlineNotificationFeignClient;


    /**
     * Метод для отправки уведомлений о сроках сотрудникам.
     * Этот метод выполняется с заданным интервалом времени и отправляет уведомления по электронной почте.
     */
    @Override
    @Scheduled(fixedDelayString = "${interval2}")
    public void deadlineNotification() {
        log.info("The data synchronization method has started, it starts every day");
        Collection<EmailAndIdDto> emailsAndIds = deadlineResolutionFeignClient.getEmailAndId();
        for (EmailAndIdDto emailAndIdDto : emailsAndIds) {
            sendDeadlineNotificationFeignClient.sendEmail(emailAndIdDto.getEmail(), String.valueOf(emailAndIdDto.getId()));
            log.info("email: " + emailAndIdDto.getEmail());
            log.info("id: " + emailAndIdDto.getId());
        }
    }
}
