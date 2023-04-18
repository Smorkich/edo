package com.education.service.nomenclature.impl;

import com.education.service.nomenclature.NomenclatureService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.constant.Constant;
import model.dto.NomenclatureDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class NomenclatureServiceImpl implements NomenclatureService {

    private final RestTemplate restTemplate;
    @Override
    public NomenclatureDto findByIndex(String index) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = URIBuilderUtil.buildURI(Constant.EDO_SERVICE_NAME, "/api/service/nomenclature/find/" + index).toString();

        return restTemplate.getForObject(uri, NomenclatureDto.class);
    }
}
