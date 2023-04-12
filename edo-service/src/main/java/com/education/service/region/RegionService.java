package com.education.service.region;

import model.dto.RegionDto;

/**
 * Service в "edo-service", служит для связи контроллера и RestTemplate
 */
public interface RegionService {

    /**
     * Метод сохранения нового региона в БД
     */
    RegionDto save(RegionDto regionDto);

    /**
     * Метод удаления региона из ДБ
     */
    void delete(long id);

    /**
     * Метод поиска региона по id
     */
    String findById(long id);

    /**
     * Метод, который возвращает все регионы
     */
    String findAll();

    /**
     * Метод, который заполняет архивную дату
     */
    void moveToArchive(Long id);

}
