package com.education.feign;

import com.education.config.FeignConfig;
import model.dto.ResolutionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

import static model.constant.Constant.RESOLUTION_URL;

@FeignClient(name = "ResolutionFeignClient", configuration = FeignConfig.class)
public interface ResolutionFeignClient {

    @PostMapping(RESOLUTION_URL + "/add")
    ResolutionDto saveResolution(@RequestBody ResolutionDto resolutionDto);

    @PostMapping( RESOLUTION_URL + "/move/{id}")
    ResolutionDto moveToArchive(@PathVariable Long id);

    @GetMapping(RESOLUTION_URL + "/{id}")
    ResolutionDto findById(@PathVariable Long id);

    @GetMapping(RESOLUTION_URL + "/all/{id}")
    Collection<ResolutionDto> findAll(@PathVariable Long id);

    @GetMapping(RESOLUTION_URL + "/notArchived/{id}")
    ResolutionDto findByIdNotArchived(@PathVariable Long id);

    @GetMapping(RESOLUTION_URL + "/notArchived/all/{id}")
    Collection<ResolutionDto> findAllByIdNotArchived(@PathVariable Long id);

}
