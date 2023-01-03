package com.education.repository.resolution;

import com.education.entity.Resolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Collection;

/**
 * Выполняет роль DAO, доступ к данным и методам
 */
@Repository
public interface ResolutionRepository extends JpaRepository<Resolution, Long> {
    /**
     * Выборка резолюции по id и при этом она не архивирована
     */
    @Query("SELECT res from Resolution res where res.id = ?1 and res.archivedDate is null")
    public Resolution findByIdAndArchivedDateIsNull(Long id);

    /**
     * Выборка всех резолюций не архивированных резолюций
     */
    @Query("SELECT res from Resolution res where res.id = ?1 and res.archivedDate is null  ")
    public Collection<Resolution> findAllByArchivedDateIsNull(Collection<Long> id);

    /**
     * Перемещение резолюции в архив
     */
    @Modifying
    @Query("UPDATE Resolution res set res.archivedDate = current_date() where res.id = ?1")
    public void movesToArchive(Long id);

}
