package com.education.feign;

import model.dto.EmailAndIdDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;

import static model.constant.Constant.DEADLINE_RESOLUTION_EMAILS_URL;
import static model.constant.Constant.EDO_REPOSITORY_NAME;

/**
 * Интерфейс Feign клиента для взаимодействия с сервисом EDO Repository.
 * Используется для получения списка адресов электронной почты исполнителей и
 * id резолюций дедлай которых уже наступил.
 *
 * @author Arkadiy_Gumelya
 */
@FeignClient(name = EDO_REPOSITORY_NAME)
public interface DeadlineResolutionFeignClient {
    /**
     * @return Список объектов {@link EmailAndIdDto},
     * содержащих адреса электронной почты и
     * id резолюций дедлай которых уже наступил.
     */
    @GetMapping(DEADLINE_RESOLUTION_EMAILS_URL)
    Collection<EmailAndIdDto> getEmailAndId();

}