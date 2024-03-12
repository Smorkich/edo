package com.education.feign;

import model.dto.AppealDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

import static model.constant.Constant.EDO_SERVICE_NAME;
import static model.constant.Constant.SERVICE_APPEAL_URL;

@FeignClient(name = "AppealFeignClient")
public interface AppealFeignClient {

    @GetMapping(SERVICE_APPEAL_URL)
    ResponseEntity<Collection<AppealDto>> findAll();
}
