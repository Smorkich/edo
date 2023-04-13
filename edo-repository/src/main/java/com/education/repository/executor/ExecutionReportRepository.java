package com.education.repository.executor;

import com.education.entity.ExecutionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ExecutionReportRepository extends JpaRepository<ExecutionReport, Long> {

    @Query("SELECT DISTINCT e FROM ExecutionReport e LEFT JOIN FETCH e.executor LEFT JOIN FETCH e.resolution where e.id =:id")
    Optional<ExecutionReport> findById(@Param("id") Long id);


}
