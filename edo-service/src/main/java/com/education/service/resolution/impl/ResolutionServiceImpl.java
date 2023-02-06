package com.education.service.resolution.impl;

import com.education.service.resolution.ResolutionService;
import lombok.AllArgsConstructor;
import model.dto.ResolutionDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;


@AllArgsConstructor
@Service
public class ResolutionServiceImpl implements ResolutionService {

    private RestTemplate restTemplate;


    @Override
    public void save(ResolutionDto resolutionDto) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/add");
        restTemplate.postForObject(builder.toString(), resolutionDto, ResolutionDto.class);
    }

    @Override
    public void moveToArchive(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/move/")
                .setPath(String.valueOf(id));
        restTemplate.postForObject(builder.toString(), null, ResolutionDto.class);
    }

    @Override
    public ResolutionDto findById(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), ResolutionDto.class);
    }

    @Override
    public Collection<ResolutionDto> findAllById(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/all/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), List.class);
    }

    @Override
    public ResolutionDto findByIdNotArchived(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/notArchived/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), ResolutionDto.class);
    }

    @Override
    public Collection<ResolutionDto> findAllByIdNotArchived(Long id) {
        var builder = buildURI(EDO_REPOSITORY_NAME, RESOLUTION_URL)
                .setPath("/notArchived/all/")
                .setPath(String.valueOf(id));
        return restTemplate.getForObject(builder.toString(), Collection.class);
    }
}
