package com.education.service.region.imp;

import com.education.entity.Region;
import com.education.repository.region.RegionRepository;
import com.education.service.region.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Service в "edo-repository", служит для связи контроллера и репозитория
 */
@Service
@AllArgsConstructor
public class RegionServiceImpl implements RegionService {

    /**
     * Поле "regionRepository" нужно для вызова Repository-слоя (edo-repository),
     * который нужен для связи с БД
     */
    final private RegionRepository regionRepository;

    /**
     * Метод сохранения нового региона в БД
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Region region) {
        regionRepository.save(region);
    }

    /**
     * Метод удаления региона из БД
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Region region) {
        regionRepository.delete(region);
    }

    /**
     * Метод поиска региона по id
     */
    @Transactional(readOnly = true)
    @Override
    public Region findById(Long id) {
        return regionRepository.findById(id).orElse(null);
    }

    /**
     * Метод, который возвращает все регионы
     */
    @Transactional(readOnly = true)
    @Override
    public Collection<Region> findAll() {
        return regionRepository.findAll();
    }

    /**
     * Метод, который заполняет архивную дату
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveToArchive(Long id) {
        regionRepository.moveToArchive(id);
    }
}
