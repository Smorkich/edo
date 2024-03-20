package com.education.repository.deadlineResolution;

import com.education.entity.DeadlineResolution;
import model.dto.EmailAndIdDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeadlineResolutionRepository extends JpaRepository<DeadlineResolution, Long> {

    /**
     * Ищем все переносы крайнего срока, по id резолюции
     */
    @Query(nativeQuery = true, value = "SELECT * FROM deadline_resolution WHERE resolution_id = :resolutionId")
    List<DeadlineResolution> findByResolutionId(@Param("resolutionId") Long resolutionId);


    /**
     * Получаем список email всех исполнителей и id резолюци у которых наступил дедлайн
     */
    @Query(nativeQuery = true,
            value = "SELECT employee.email, deadline_resolution.resolution_id " +
                    "FROM deadline_resolution " +
            "INNER JOIN resolution ON deadline_resolution.resolution_id = resolution.id " +
            "INNER JOIN resolution_executor ON resolution.id = resolution_executor.resolution_id " +
            "INNER JOIN employee ON resolution_executor.employee_id = employee.id " +
            "WHERE deadline_resolution.deadline <= now()")
    List<EmailAndIdDto> findAllExecutorEmails();

}