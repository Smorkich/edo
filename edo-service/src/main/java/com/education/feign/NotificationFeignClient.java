package com.education.feign;

import jakarta.validation.Valid;
import model.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.NOTIFICATION_URL;

@FeignClient(name = EDO_REPOSITORY_NAME)
public interface NotificationFeignClient {

    @GetMapping(NOTIFICATION_URL + "{/id}")
    NotificationDto findByID(@PathVariable Long id);

    @GetMapping(NOTIFICATION_URL)
    Collection <NotificationDto> findAll();

    @GetMapping(NOTIFICATION_URL + "{/ids}")
    Collection <NotificationDto> findAllById(@PathVariable List<Long> ids);

    @GetMapping(NOTIFICATION_URL)
    NotificationDto save(@RequestBody @Valid NotificationDto notificationDto);

    @DeleteMapping(NOTIFICATION_URL + "{/id}")
    HttpStatus delete(@PathVariable Long id);

}
