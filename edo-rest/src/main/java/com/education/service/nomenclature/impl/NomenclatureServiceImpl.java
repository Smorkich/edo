package com.education.service.nomenclature.impl;

import com.education.service.nomenclature.NomenclatureService;
import com.education.util.KeySwitcherUtil;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.NomenclatureDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static model.constant.Constant.*;

@Service
@AllArgsConstructor
@Log4j2
public class NomenclatureServiceImpl implements NomenclatureService {

    private final RestTemplate restTemplate;
    private final String NOMENCLATURE_URL = "api/service/nomenclature/index";


    /**
     * Ищет номенклатуру по индексу /find/согл
     * @param index
     * @return
     */
    @Override
    public List<NomenclatureDto> findByIndex(String index) {

        var correctIndex = KeySwitcherUtil.transliterate(index);
        log.info("передаем index {} в edo-service", correctIndex);
        URL uri = null;
        try {
            uri = URIBuilderUtil.buildURI(EDO_SERVICE_NAME, NOMENCLATURE_URL)
                        .addParameter(NOMENCLATURE_PARAMETER, correctIndex).build().toURL();
            log.info("URL after BUILDER {}", uri);
            return restTemplate.getForObject(uri.toURI(), List.class);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
