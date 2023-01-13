package com.education.service.resolution.impl;

import com.education.service.resolution.ResolutionService;
import lombok.AllArgsConstructor;
import model.dto.ResolutionDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@Service
public class ResolutionServiceImpl implements ResolutionService {

    private RestTemplate restTemplate;
    private final String URL = "http://edo-repository/api/repository/resolution";

    @Override
    public void save(ResolutionDto resolutionDto) {
        restTemplate.postForObject(URL + "/add", resolutionDto, ResolutionDto.class);
    }

    @Override
    public void moveToArchive(Long id) {
        restTemplate.postForObject(URL + "/move/" + id, null, ResolutionDto.class);
    }

    @Override
    public ResolutionDto findById(Long id) {
        return restTemplate.getForObject(URL + "/{id}", ResolutionDto.class);

    }

    @Override
    public Collection<ResolutionDto> findAllById(Long id) {
        return restTemplate.getForObject(URL + "/all/" + id, List.class);
    }

    @Override
    public ResolutionDto findByIdNotArchived(Long id) {

        return restTemplate.getForObject(URL + "/notArchived/" + id, ResolutionDto.class);
    }

    @Override
    public Collection<ResolutionDto> findAllByIdNotArchived(Long id) {
        return restTemplate.getForObject(URL + "/notArchived/all/" + id, Collection.class);
    }
}
