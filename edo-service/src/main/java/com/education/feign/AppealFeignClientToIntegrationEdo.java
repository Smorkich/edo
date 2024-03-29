package com.education.feign;

import model.dto.AppealDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Map;

import static model.constant.Constant.*;

@FeignClient(name = EDO_INTEGRATION_NAME, url = MESSAGE_URL)
public interface AppealFeignClientToIntegrationEdo extends BaseFeignClient<AppealDto> {

    @PostMapping
    void sendMessage(@RequestBody Map<String,Object> messageData);
}
