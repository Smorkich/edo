package com.education.repository.executor;

import com.education.entity.ExecutionReport;
import com.education.projection.ExecutionReportProjectionForAppealFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface ExecutionReportRepository extends JpaRepository<ExecutionReport, Long> {

    @Query("SELECT DISTINCT e FROM ExecutionReport e " +
            "LEFT JOIN FETCH e.executor " +
            "LEFT JOIN FETCH e.resolution " +
            "WHERE e.id =:id")
    Optional<ExecutionReport> findById(@Param("id") Long id);

    /**
     * Receive resolution statuses
     */
    @Query("SELECT e FROM ExecutionReport e " +
            "WHERE e.resolution.id IN :resolutionId")
    Collection<ExecutionReportProjectionForAppealFile> getResolutionStatus(@Param("resolutionId") List<Long> resolutionId);

}
