package com.education.repository.question;

import com.education.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Nadezhda Pupina
 * JPA DATA Repository for edo-repository service
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Query request for set a date to archived_date field
     */
    @Modifying
    @Query(value = "update Question u set u.archivedDate =:archDate where u.id =:id")
    void moveToArchive(@Param("archDate") ZonedDateTime date, @Param("id") Long id);

    /**
     * Query request for searching not archived entity by id
     */
    @Query("select u from Question u where u.archivedDate is null and u.id =:id")
    Optional<Question> findByIdNotArchived(@Param("id") Long id);

    /**
     * Query request for searching not archived entities by ids
     */
    @Query("select u from Question u where u.archivedDate is null and u.id in :idList")
    List<Question> findAllByIdNotArchived(@Param("idList") Collection<Long> idList);
}
