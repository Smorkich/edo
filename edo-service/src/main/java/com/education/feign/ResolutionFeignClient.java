package com.education.feign;

import com.education.config.FeignConfig;
import model.dto.ResolutionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static model.constant.Constant.RESOLUTION_URL;

@FeignClient(name = "ResolutionFeignClient", configuration = FeignConfig.class)
public interface ResolutionFeignClient {

    @PostMapping(RESOLUTION_URL + "/add")
    ResolutionDto saveResolution(@RequestBody ResolutionDto resolutionDto);

    @PatchMapping( RESOLUTION_URL + "/move/{id}")
    String moveToArchive(@PathVariable Long id);

    @PostMapping(RESOLUTION_URL + "/unarchive/{resolutionId}")
    ResolutionDto unarchiveResolution(@PathVariable Long resolutionId);

    @GetMapping(RESOLUTION_URL + "/{id}")
    ResolutionDto findById(@PathVariable Long id);

    @GetMapping(RESOLUTION_URL + "/all/{id}")
    Collection<ResolutionDto> findAll(@PathVariable Long id);

    @GetMapping(RESOLUTION_URL + "/notArchived/{id}")
    ResolutionDto findByIdNotArchived(@PathVariable Long id);

    @GetMapping(RESOLUTION_URL + "/notArchived/all/{id}")
    Collection<ResolutionDto> findAllByIdNotArchived(@PathVariable Long id);

    @GetMapping(RESOLUTION_URL + "/appealId/all/{appealId}")
    Collection<ResolutionDto> findAllByAppealIdAndIsDraftFalse(@PathVariable Long appealId);
}
