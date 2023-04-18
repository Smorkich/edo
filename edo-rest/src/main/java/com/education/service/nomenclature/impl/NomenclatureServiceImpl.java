package com.education.service.nomenclature.impl;

import com.education.service.nomenclature.NomenclatureService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.constant.Constant;
import model.dto.NomenclatureDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class NomenclatureServiceImpl implements NomenclatureService {

    private final RestTemplate restTemplate;
    private final String NOMENCLATURE_URL = "api/service/nomenclature/find";

    /**
     * Ищет номенклатуру по индексу /find/согл
     * @param index
     * @return
     */
    @Override
    public List<NomenclatureDto> findByIndex(String index) throws URISyntaxException, MalformedURLException {
        log.info("передаем index {} в edo-service", index);
        var uri = URIBuilderUtil.buildURI(Constant.EDO_SERVICE_NAME, NOMENCLATURE_URL)
                .addParameter("index", index).build().toURL();
        log.info("URL after BUILDER {}", uri.toURI());
        return restTemplate.getForObject(uri.toURI(), List.class);
    }
}
