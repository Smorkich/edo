package com.education.feign;

import jakarta.validation.Valid;
import model.dto.AddressDto;
import model.dto.AppealFileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static model.constant.Constant.*;

@FeignClient(name = EDO_REPOSITORY_NAME)
public interface AppealFeignClient {

    @GetMapping(APPEAL_URL + "/download/xlsx/{appealId}")
    Collection<AppealFileDto> findAllByAppealIdForXLSX(@PathVariable Long appealId);
}
