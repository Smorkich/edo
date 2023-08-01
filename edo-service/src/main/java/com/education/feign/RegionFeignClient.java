package com.education.feign;

import com.education.config.FeignConfig;
import jakarta.validation.Valid;
import model.dto.RegionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;

import static model.constant.Constant.*;

@FeignClient(name = "RegionFeignClient", configuration = FeignConfig.class)
public interface RegionFeignClient {
    @PostMapping(REGION_URL)
    ResponseEntity<RegionDto> save(@RequestBody @Valid RegionDto regionDto);

    @DeleteMapping(REGION_URL + "/{id}")
    ResponseEntity<RegionDto> delete(@PathVariable Long id);

    @GetMapping(REGION_URL + "/{id}")
    ResponseEntity<RegionDto> findById(@PathVariable long id);

    @GetMapping(REGION_URL + "/all")
    ResponseEntity<Collection<RegionDto>> findAll();

    @PostMapping(REGION_URL + "/{id}")
    ResponseEntity<RegionDto> moveToArchive(@PathVariable Long id);

}
