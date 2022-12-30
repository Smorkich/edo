package com.education.repository.filePool;

import com.education.entity.FilePool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilePoolRepository extends JpaRepository<FilePool, Long> {
}
