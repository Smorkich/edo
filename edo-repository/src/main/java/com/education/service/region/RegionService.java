package com.education.service.region;

import com.education.entity.Region;

import java.util.Collection;

/**
 * Service в "edo-repository", служит для связи контроллера и репозитория
 */
public interface RegionService {
    /**
     * Метод сохранения нового региона в БД
     */
    void save(Region region);

    /**
     * Метод удаления региона из ДБ
     */
    void delete(Region region);

    /**
     * Метод поиска региона по id
     */
    Region findById(Long id);

    /**
     * Метод, который возвращает все регионы
     */
    Collection<Region> findAll();

    /**
     * Метод, который заполняет архивную дату
     */
    void moveToArchive(Long id);

}
