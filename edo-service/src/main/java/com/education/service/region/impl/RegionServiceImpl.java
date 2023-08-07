package com.education.service.region.impl;

import com.education.feign.RegionFeignClient;
import com.education.service.region.RegionService;
import lombok.AllArgsConstructor;
import model.dto.RegionDto;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class RegionServiceImpl implements RegionService {

    /**
     * Поле "restTemplate" нужно для вызова RestTemplate,
     * который нужен для совершения CRUD-операции по заданному URL
     */
    private final RegionFeignClient regionFeignClient;

    /**
     * Метод сохранения нового региона в БД
     */
    @Override
    public RegionDto save(RegionDto regionDto) {
        return regionFeignClient.save(regionDto).getBody();
    }
    /**
     * Метод удаления региона из ДБ
     */
    @Override
    public void delete(long id) {
        regionFeignClient.delete(id);
    }

    /**
     * Метод, который возвращает регионы по его id
     */
    @Override
    public RegionDto findById(long id) {
        return regionFeignClient.findById(id).getBody();
    }

    /**
     * Метод, который возвращает все регионы
     */
    @Override
    public Collection<RegionDto> findAll() {
        return regionFeignClient.findAll().getBody();
    }

    /**
     * Метод, который заполняет архивную дату
     */
    @Override
    public void moveToArchive(Long id) {
        regionFeignClient.moveToArchive(id);
    }
}
