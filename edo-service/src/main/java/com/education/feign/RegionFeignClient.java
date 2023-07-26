package com.education.feign;

import jakarta.validation.Valid;
import model.dto.RegionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.REGION_URL;

@FeignClient(name = EDO_REPOSITORY_NAME)
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
