package com.education.service.theme;

import com.education.entity.Theme;

import java.net.URISyntaxException;
import java.util.Collection;

/**
 *@author AlexeySpiridonov
 */

public interface ThemeService {

    /**
     * save - сохраняет новый файл в бд
     */
    void save(Theme theme) throws URISyntaxException;

    /**
     * delete - удаляет файл из бд
     */
    void delete(Long id) throws URISyntaxException;

    /**
     * findById - находит файл по id
     */
    Theme findById(Long id) throws URISyntaxException;

    /**
     * findAllById - выводит список всех файлов по id
     */
//    Collection<Theme> findAllById(Iterable<Long> ids);

    /**
     * findAll - возвращает все темы
     */
    Collection<Theme> findAll() throws URISyntaxException;

    /**
     * moveToArchive - заполняет архивную дату
     */
    void moveToArchive(Long id) throws URISyntaxException;

    /**
     * findByIdNotArchived - находит файл без архивной даты по id
     */
    Theme findByIdAndArchivedDateNull(Long id) throws URISyntaxException;

    /**
     * findAllByIdNotArchived - находит все файлы без архивной даты по id
     */
    Collection<Theme> findByIdInAndArchivedDateNull(Iterable<Long> ids) throws URISyntaxException;
}
