package com.education.feign;

import model.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import static model.constant.Constant.CURRENT_USER_REST_URL;
import static model.constant.Constant.EDO_REST_NAME;

@FeignClient(name = EDO_REST_NAME, url = CURRENT_USER_REST_URL)
public interface CurrentUserFeignClient {
    @GetMapping()
    ResponseEntity<EmployeeDto> getCurrentUser();
}
