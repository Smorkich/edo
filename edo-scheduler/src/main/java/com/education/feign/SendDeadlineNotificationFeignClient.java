package com.education.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static model.constant.Constant.EDO_INTEGRATION_NAME;
import static model.constant.Constant.MESSAGE_URL_DEADLINE;

/**
 * Интерфейс Feign клиента для отправки уведомлений о сроках насиупления deadline.
 * Используется для взаимодействия с сервисом интеграции EDO,
 * отправляя уведомления по электронной почте.
 *
 * @author Arkadiy_Gumelya
 */

@FeignClient(name = EDO_INTEGRATION_NAME, configuration = MyFeignConfig.class)
public interface SendDeadlineNotificationFeignClient {
    /**
     * Отправляет уведомление по электронной почте с сообщением о сроке.
     *
     * @param email   Адрес электронной почты получателя уведомления.
     * @param message Сообщение уведомления.
     */
    @PostMapping(MESSAGE_URL_DEADLINE)
    String sendEmail(@RequestParam("email") String email,
                     @RequestParam("message") String message);
}
