package com.education.service.facsimile.impl;

import com.education.service.facsimile.FacsimileService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.dto.FacsimileDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import static model.constant.Constant.EDO_SERVICE_NAME;

@Service
@AllArgsConstructor
public class FacsimileServiceImpl implements FacsimileService {

    private RestTemplate restTemplate;
    @Override
    public FacsimileDto save(FacsimileDto facsimileDto) {
        String uri = URIBuilderUtil.buildURI(EDO_SERVICE_NAME,"/api/service/facsimile/save").toString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.exchange(uri, HttpMethod.POST,new HttpEntity<>(facsimileDto,headers),FacsimileDto.class).getBody();
    }
}
