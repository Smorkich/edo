package com.education.repository.theme;

import com.education.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
*@author AlexeySpiridonov
*/

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Modifying
    @Query(value = "update  theme set  theme.archivedDate =:archDate where theme.id =:id")
    void moveToArchive(@Param("archDate") ZonedDateTime date, @Param("id") Long id);

    Theme findByIdAndArchivedDateNull(Long id);
    List<Theme> findByIdInAndArchivedDateNull(Iterable<Long> ids);
}
