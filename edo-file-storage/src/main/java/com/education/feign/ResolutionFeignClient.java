package com.education.feign;

import model.dto.ResolutionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static model.constant.Constant.EDO_SERVICE_NAME;
import static model.constant.Constant.RESOLUTION_SERVICE_URL;

@FeignClient(name = EDO_SERVICE_NAME, url = RESOLUTION_SERVICE_URL)
public interface ResolutionFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<ResolutionDto> findById(@PathVariable Long id);
}
