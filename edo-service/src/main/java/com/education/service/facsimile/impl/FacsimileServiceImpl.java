package com.education.service.facsimile.impl;

import com.education.service.facsimile.FacsimileService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.constant.Constant;
import model.dto.FacsimileDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class FacsimileServiceImpl implements FacsimileService {

    private final RestTemplate restTemplate;

    @Override
    public FacsimileDto save(FacsimileDto facsimileDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = URIBuilderUtil.buildURI(Constant.EDO_REPOSITORY_NAME, "api/repository/facsimile/save").toString();
        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(facsimileDto, headers), FacsimileDto.class).getBody();

    }
}
