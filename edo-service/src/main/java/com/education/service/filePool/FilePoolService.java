package com.education.service.filePool;
import model.dto.FilePoolDto;
import org.springframework.web.multipart.MultipartFile;

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
    FilePoolDto save(FilePoolDto filePool);

    /**
     * delete - удаляет файл из бд
     */
    void delete(Long id);

    /**
     * findById - находит файл по id
     */
    String findById(Long id);

    /**
     * findAll - возвращает все файлы
     */
    String findAll();

    /**
     * findAllById - выводит список всех файлов по id
     */
    Collection<FilePoolDto> findAllById(String ids);

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
    Collection<FilePoolDto> findAllByIdNotArchived(String ids);

}
