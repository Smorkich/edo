package com.education.feign;

import model.dto.NomenclatureDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static model.constant.Constant.EDO_REPOSITORY_NAME;
import static model.constant.Constant.NOMENCLATURE_URL;

@FeignClient(name=EDO_REPOSITORY_NAME)
public interface NomenclatureFeignClient {

    @GetMapping(NOMENCLATURE_URL + "/index")
    List<NomenclatureDto> findByIndex(@RequestParam("index") String index);
}
