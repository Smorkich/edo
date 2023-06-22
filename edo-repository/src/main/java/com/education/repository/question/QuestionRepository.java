package com.education.repository.question;

import com.education.entity.Question;
import model.enum_.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * @author Nadezhda Pupina
 * JPA DATA Repository for edo-repository service
 */

public interface QuestionRepository extends JpaRepository<Question, Long> {
    /**
     * Запрос на установку даты в поле archived_date
     */
    @Modifying
    @Query(nativeQuery = true, value = "update question set archived_date = now() where id =:id and archived_date is null")
    void moveToArchive(@Param("id") Long id);

    /**
     * Запрос на поиск объекта вне архива по id
     */
    Question findByIdAndArchivedDateNull(@Param("id") Long id);

    /**
     * Запрос на поиск объектов вне архива по id
     */
    @Query("select u from Question u  where u.archivedDate is null and u.id in :idList")
    List<Question> findByIdInAndArchivedDateNull(@Param("idList") Iterable<Long> ids);

    /**
     * Запрос на поиск по id Appeal'a всех вопросов относящихся к нему
     *
     * @param id - id Appeal'a, вопросы которого хотим получить
     * @return Collection<Question> - коллекция из вопросов Appeal'a
     */
    @Query("select u from Question u join u.appeal a where a.id in :id")
    Set<Question> findByAppealId(@Param("id") Long id);

    /**
     * Запрос на изменение статуса вопроса в поле question_status
     *
     * @param questionId - id вопроса, статус которого хотим изменить
     * @param status     - статус на который хотим изменить поле question_status (статусы находятся в edo-common, в enum_)
     */
    @Modifying
    @Query("update Question q set q.status = :newStatus where q.id = :questionId")
    void updateQuestionStatus(@Param("questionId") Long questionId, @Param("newStatus") Status status);

}
