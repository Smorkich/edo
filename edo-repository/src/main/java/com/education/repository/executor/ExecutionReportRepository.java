package com.education.repository.executor;

import com.education.entity.ExecutionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ExecutionReportRepository extends JpaRepository<ExecutionReport, Long> {

    @Query("SELECT DISTINCT e FROM ExecutionReport e " +
            "LEFT JOIN FETCH e.executor " +
            "LEFT JOIN FETCH e.resolution " +
            "WHERE e.id =:id")
    Optional<ExecutionReport> findById(@Param("id") Long id);

    /**
     * Receive resolution status
     */
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN 'Исполнено' ELSE 'Не исполнено' END FROM ExecutionReport e " +
            "WHERE e.resolution.id = :resolutionId AND e.status = model.enum_.Status.PERFORMED")
    String resolutionStatus(@Param("resolutionId") Long resolutionId);


}
