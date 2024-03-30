package com.education.feign;

import model.dto.AppealFileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

import static model.constant.Constant.EDO_SERVICE_NAME;
import static model.constant.Constant.RESOLUTION_SERVICE_URL;

@FeignClient(name = EDO_SERVICE_NAME, contextId = "ResolutionFeignClient")
public interface ResolutionFeignClient {

    @GetMapping(RESOLUTION_SERVICE_URL + "/appeal/xlsx/{appealId}")
    Collection<AppealFileDto> findAllByAppealIdForXLSX(@PathVariable("appealId") Long appealId);

}
