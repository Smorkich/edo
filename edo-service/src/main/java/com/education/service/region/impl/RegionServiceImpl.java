package com.education.service.region.impl;

import com.education.service.region.RegionService;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import model.dto.RegionDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static model.constant.Constant.*;

@Service
@AllArgsConstructor
public class RegionServiceImpl implements RegionService {

    /**
     * Поле "restTemplate" нужно для вызова RestTemplate,
     * который нужен для совершения CRUD-операции по заданному URL
     */
    private final RestTemplate restTemplate;

    /**
     * Метод сохранения нового региона в БД
     */
    @Override
    public RegionDto save(RegionDto regionDto) {
        var builder = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "api/repository/region/")
                .toString();
        return restTemplate.postForObject(builder, regionDto, RegionDto.class);
    }

    /**
     * Метод удаления региона из ДБ
     */
    @Override
    public void delete(long id) {
        var builder = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "api/repository/region/" + id)
                .toString();
        restTemplate.delete(builder);
    }

    /**
     * Метод, который возвращает регионы по его id
     */
    @Override
    public String findById(long id) {
        var builder = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "api/repository/region/" + id)
                .toString();
        return restTemplate.getForObject(builder, String.class);
    }

    /**
     * Метод, который возвращает все регионы
     */
    @Override
    public String findAll() {
        var builder = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "api/repository/region/" + "all")
                .toString();
        return restTemplate.getForObject(builder, String.class);
    }

    /**
     * Метод, который заполняет архивную дату
     */
    @Override
    public void moveToArchive(Long id) {
        var builder = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "api/repository/region/" + id)
                .toString();
        restTemplate.postForObject(builder, null, String.class);
    }
}
