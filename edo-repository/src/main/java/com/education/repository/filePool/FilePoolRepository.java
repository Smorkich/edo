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
    FilePool findByIdAndArchivedDateNull(Long id);

    List<FilePool> findByIdInAndArchivedDateNull(Iterable<Long> ids);

}
