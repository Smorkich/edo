package com.education.repository.filePool;

import com.education.entity.FilePool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nadezhda Pupina
 * Наследует Jpa repository, реализует необходимые методы
 */
@Repository
public interface FilePoolRepository extends JpaRepository<FilePool, Long> {
    /**
     * Возвращает по id файл вне архива
     * @return
     */
    FilePool findByIdAndArchivedDateNull(Long id);
    /**
     * Возвращает по id все файлы вне архива
     * @return
     */
    List<FilePool> findByIdInAndArchivedDateNull(Iterable<Long> ids);

}

