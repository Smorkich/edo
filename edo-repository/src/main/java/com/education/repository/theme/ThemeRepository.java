package com.education.repository.theme;

import com.education.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*@author AlexeySpiridonov
*/

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    Theme findByIdAndArchivedDateNull(Long id);
    List<Theme> findByIdInAndArchivedDateNull(Iterable<Long> ids);
}
