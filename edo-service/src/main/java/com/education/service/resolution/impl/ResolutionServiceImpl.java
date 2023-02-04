package com.education.service.resolution.impl;

import com.education.service.resolution.ResolutionService;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import model.dto.ResolutionDto;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;


@AllArgsConstructor
@Service
public class ResolutionServiceImpl implements ResolutionService {

    private RestTemplate restTemplate;


    @Override
    public void save(ResolutionDto resolutionDto) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/add");
        restTemplate.postForObject(builder.build(), resolutionDto, ResolutionDto.class);
    }

    @Override
    public void moveToArchive(Long id) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/move/")
                .setPath(String.valueOf(id));
        restTemplate.postForObject(builder.build(), null, ResolutionDto.class);
    }

    @Override
    public ResolutionDto findById(Long id) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), ResolutionDto.class);
    }

    @Override
    public Collection<ResolutionDto> findAllById(Long id) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/all/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), List.class);
    }

    @Override
    public ResolutionDto findByIdNotArchived(Long id) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/notArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), ResolutionDto.class);
    }

    @Override
    public Collection<ResolutionDto> findAllByIdNotArchived(Long id) throws URISyntaxException {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/notArchived/all/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.build(), Collection.class);
    }
}
