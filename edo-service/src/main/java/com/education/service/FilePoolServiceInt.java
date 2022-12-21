package com.education.service;
import model.dto.FilePoolDto;

import java.util.Collection;

public interface FilePoolServiceInt {

    /**
     * save - сохраняет новый файл в бд
     */
    void save(FilePoolDto filePool);

    /**
     * delete - удаляет файл из бд
     */
    void delete(Long id);

    /**
     * findById - находит файл по id
     */
    FilePoolDto findById(Long id);

    /**
     * findAllById - выводит список всех файлов по id
     */
    Collection<FilePoolDto> findAllById(Iterable<Long> ids);

    Collection<FilePoolDto> findAll();

    /**
     * moveToArchive - заполняет архивную дату
     */
    void moveToArchive(Long id);

    /**
     * findByIdNotArchived - находит файл без архивной даты по id
     */
    FilePoolDto findByIdNotArchived(Long id);

    /**
     * findAllByIdNotArchived - находит все файлы без архивной даты по id
     */
    Collection<FilePoolDto> findAllByIdNotArchived(Iterable<Long> ids);

}
