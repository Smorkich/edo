package com.education.repository.filePool;

import com.education.entity.FilePool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
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

    /**
     * При архивации файла добавляет дату архивации
     * @return
     */
    @Modifying
    @Query("UPDATE FilePool d  SET d.archivedDate = :date WHERE d.id = :id and d.archivedDate is null")
    void moveToArchived(@Param(value = "date") ZonedDateTime zonedDateTime, @Param(value = "id") Long id);

}

