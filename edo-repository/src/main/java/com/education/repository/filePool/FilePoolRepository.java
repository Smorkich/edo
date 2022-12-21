package com.education.repository.filePool;

import com.education.entity.FilePool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface FilePoolRepository extends JpaRepository<FilePool, Long> {
    void moveToArchive(Long id);

    FilePool findByIdNotArchived(Long id);

    List<FilePool> findAllByIdNotArchived(Iterable<Long> ids);

}
