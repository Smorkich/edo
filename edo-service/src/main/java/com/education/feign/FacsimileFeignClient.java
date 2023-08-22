package com.education.feign;

import com.education.config.FeignConfig;
import model.dto.FacsimileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static model.constant.Constant.*;
@FeignClient(name = EDO_REPOSITORY_NAME, configuration = FeignConfig.class)
public interface FacsimileFeignClient {

    @PostMapping(value = FACSIMILE_URL + "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    FacsimileDto save(@RequestBody FacsimileDto facsimileDto);
}
