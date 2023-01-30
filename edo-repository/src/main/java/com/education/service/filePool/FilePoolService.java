package com.education.service.filePool;

import com.education.entity.FilePool;

import java.net.URISyntaxException;
import java.util.Collection;

/**
 * @author Nadezhda Pupina
 * Интерфейс с методами для FilePool
 */
public interface FilePoolService {

    /**
     * save - сохраняет новый файл в бд
     */
    FilePool save(FilePool filePool) throws URISyntaxException;

    /**
     * delete - удаляет файл из бд
     */
    void delete(Long id) throws URISyntaxException;

    /**
     * findById - находит файл по id
     */
    FilePool findById(Long id) throws URISyntaxException;

    /**
     * findAllById - выводит список всех файлов по id
     */
//    Collection<FilePool> findAllById(Iterable<Long> ids);

    /**
     * findAllById - выводит список всех файлов
     */
    Collection<FilePool> findAll() throws URISyntaxException;

    /**
     * moveToArchive - заполняет архивную дату
     */
    void moveToArchive(Long id) throws URISyntaxException;

    /**
     * findByIdNotArchived - находит файл без архивной даты по id
     */
    FilePool findByIdAndArchivedDateNull(Long id) throws URISyntaxException;

    /**
     * findAllByIdNotArchived - находит все файлы без архивной даты по id
     */
    Collection<FilePool> findByIdInAndArchivedDateNull(Iterable<Long> ids) throws URISyntaxException;


}
