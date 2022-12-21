package com.education.serivce.filePoll;

import com.education.entity.FilePool;

import java.time.ZonedDateTime;
import java.util.Collection;

public interface FilePoolService {

    /**
     * save - сохраняет новый файл в бд
     */
    void save(FilePool filePool);

    /**
     * delete - удаляет файл из бд
     */
    void delete(Long id);

    /**
     * findById - находит файл по id
     */
    FilePool findById(Long id);

    /**
     * findAllById - выводит список всех файлов по id
     */
    Collection<FilePool> findAllById(Iterable<Long> ids);

    Collection<FilePool> findAll();

    /**
     * moveToArchive - заполняет архивную дату
     */
    void moveToArchive(Long id);

    /**
     * findByIdNotArchived - находит файл без архивной даты по id
     */
    FilePool findByIdNotArchived(Long id);

    /**
     * findAllByIdNotArchived - находит все файлы без архивной даты по id
     */
    Collection<FilePool> findAllByIdNotArchived(Iterable<Long> ids);


}
