package com.education.repository.deadlineResolution;

import com.education.entity.DeadlineResolution;
import model.dto.EmailAndIdDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

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
    @Query("SELECT new model.dto.EmailAndIdDto(e.email, r.id) FROM DeadlineResolution dr " +
            "JOIN dr.resolution r " +
            "JOIN r.executor e " +
            "WHERE dr.deadline <= current_timestamp")
    List<EmailAndIdDto> findAllExecutorEmails();



    /**
     * Receive last deadline associated with resolution
     */
    @Query(nativeQuery = true, value = "SELECT * FROM deadline_resolution dl " +
            "WHERE dl.resolution_id = :resolutionId " +
            "ORDER BY dl.deadline DESC " +
            "LIMIT 1")
    Optional<DeadlineResolution> findLastDeadlineByResolutionId(@Param("resolutionId") Long resolutionId);
}

