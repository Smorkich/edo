package com.education.feign;

import com.education.config.FeignConfig;
import model.dto.NomenclatureDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static model.constant.Constant.NOMENCLATURE_URL;

@FeignClient(name = "NomenclatureFeignClient", configuration = FeignConfig.class)
public interface NomenclatureFeignClient {

    @DeleteMapping(NOMENCLATURE_URL + "/delete/{id}")
    String deleteById(@PathVariable Long id);

    @GetMapping(NOMENCLATURE_URL + "/find/{id}")
    NomenclatureDto findByID(@PathVariable Long id);

    @GetMapping(NOMENCLATURE_URL + "/index")
    List<NomenclatureDto> findByIndex(@RequestParam("index") String index);

    @GetMapping(NOMENCLATURE_URL + "/all")
    List<NomenclatureDto> findAllByIdController(@RequestParam("id") String ids);

    @GetMapping(NOMENCLATURE_URL + "/move")
    NomenclatureDto move(@PathVariable Long id);

    @GetMapping(NOMENCLATURE_URL + "/notArch/{id}")
    NomenclatureDto findByIdNotArchivedController(@PathVariable Long id);

    @GetMapping(NOMENCLATURE_URL + "/notArchList")
    List<NomenclatureDto> findAllByIdNotArchivedController(@RequestParam("id") String ids);

    @PostMapping(NOMENCLATURE_URL + "/add")
    NomenclatureDto saveDefaultEntity(@RequestBody NomenclatureDto nomenclatureDto);

}
