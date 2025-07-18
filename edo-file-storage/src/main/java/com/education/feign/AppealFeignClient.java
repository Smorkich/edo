package com.education.feign;

import model.dto.AppealFileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

import static model.constant.Constant.*;

@FeignClient(name = EDO_SERVICE_NAME, contextId = "AppealFeignClient")
public interface AppealFeignClient {

    @GetMapping(SERVICE_APPEAL_URL + "/download/xlsx/{appealId}")
    Collection<AppealFileDto> findAllByAppealIdForXLSX(@PathVariable("appealId") Long appealId);

}
