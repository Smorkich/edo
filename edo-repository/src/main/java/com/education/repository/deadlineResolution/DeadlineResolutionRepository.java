package com.education.repository.deadlineResolution;

import com.education.entity.DeadlineResolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeadlineResolutionRepository extends JpaRepository<DeadlineResolution, Long> {

    /**
     * Ищем все переносы крайнего срока, по id резолюции
     */
    @Query(nativeQuery = true, value = "SELECT * FROM deadline_resolution WHERE resolution_id = :resolutionId")
    List<DeadlineResolution> findByResolutionId(@Param("resolutionId") Long resolutionId);
}

