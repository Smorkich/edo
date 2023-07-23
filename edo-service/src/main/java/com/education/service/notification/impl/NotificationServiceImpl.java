package com.education.service.notification.impl;

import com.education.feign.NotificationFeignClient;
import com.education.service.notification.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.NotificationDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

import static com.education.util.URIBuilderUtil.buildURI;

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.NOTIFICATION_URL;


@Service
@Log4j2
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationFeignClient notificationFeignClient;

    @Override
    public NotificationDto findById(Long id) {
        return notificationFeignClient.findByID(id);
    }

    @Override
    public Collection<NotificationDto> findAll() {
        return notificationFeignClient.findAll();
    }

    @Override
    public Collection<NotificationDto> findAllById(List<Long> ids) {
        return notificationFeignClient.findAllById(ids);
    }
    @Override
    public NotificationDto save(NotificationDto notification) {
        return notificationFeignClient.save(notification);
    }

    @Override
    public void delete(Long id) {
        notificationFeignClient.delete(id);
    }

}