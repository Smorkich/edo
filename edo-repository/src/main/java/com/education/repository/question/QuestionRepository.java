package com.education.repository.question;

import com.education.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Nadezhda Pupina
 * JPA DATA Repository for edo-repository service
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Запрос на установку даты в поле archived_date
     */
    @Modifying
    @Query(value = "update Question u set u.archivedDate =:date where u.id =:id and u.archivedDate is null")
    void moveToArchive(@Param("date") ZonedDateTime date, @Param("id") Long id);

    /**
     * Запрос на поиск объекта вне архива по id
     */
    Question findByIdAndArchivedDateNull(Long id);

    /**
     * Запрос на поиск объектов вне архива по id
     */
    List<Question> findByIdInAndArchivedDateNull(Iterable<Long> ids);

}
