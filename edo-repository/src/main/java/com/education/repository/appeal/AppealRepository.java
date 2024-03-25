package com.education.repository.appeal;

import com.education.entity.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long> {

    /**
     * Метод достает сообщение по id
     */
    Optional<Appeal> findByIdAndArchivedDateIsNull(Long id);

    @Override
    @Query("select a from Appeal a " +
            "left join fetch a.signer " +
            "left join fetch a.addressee " +
            "left join fetch a.creator " +
            "left join fetch a.authors " +
            "left join fetch a.questions " +
            "left join fetch a.file " +
            "left join fetch a.nomenclature " +
            "left join fetch a.region " +
            " where a.id = :id")
    Optional<Appeal> findById(@Param("id") Long id);

    /**
     * Метод достаёт Appeal по нужному creator_id вместе с пагинацией
     */

    @Query(nativeQuery = true, value = "SELECT * FROM Appeal a WHERE a.creator_id = :creatorId LIMIT :limit OFFSET :offset")
    List<Appeal> findByCreatorId(@Param("creatorId") Long creatorId, @Param("offset") int off, @Param("limit") int lim);


    /**
     * Метод достает Appeal, у которого поле archivedDate = null
     */
    @Query("select a from Appeal a " +
            "left join fetch a.signer " +
            "left join fetch a.addressee " +
            "left join fetch a.creator " +
            "left join fetch a.authors " +
            "left join fetch a.questions " +
            "left join fetch a.file " +
            "left join fetch a.nomenclature " +
            "left join fetch a.region     " +
            "where a.id =:id and a.archivedDate is null " +
            "order by a.id asc ")
    Optional<Appeal> findByIdNotArchived(@Param("id") Long id);

    /**
     * Метод, который достает всех Appeal, у которых поле archivedDate = null
     */
    @Query("select a from Appeal a " +
            "left join fetch a.signer " +
            "left join fetch a.addressee " +
            "left join fetch a.creator " +
            "left join fetch a.authors " +
            "left join fetch a.questions " +
            "left join fetch a.file " +
            "left join fetch a.nomenclature " +
            "left join fetch a.region " +
            "where a.archivedDate is null " +
            "order by a.id asc ")
    Collection<Appeal> findAllNotArchived();

    /**
     * Метод достает Appeal по Questions id
     */
    @Query("select a from Appeal a " +
            "left join fetch a.signer " +
            "left join fetch a.addressee " +
            "left join fetch a.creator " +
            "left join fetch a.authors " +
            "left join fetch a.file " +
            "left join fetch a.nomenclature " +
            "left join fetch a.region " +
            "left join fetch a.questions q " +
            "where q.id = :id " +
            "order by a.id asc ")
    Optional<Appeal> findAppealByQuestionsId(@Param("id") Long id);

    /**
     * изменяет статус обращения на "на рассмотрении"
     */
    @Modifying
    @Query(nativeQuery = true, value = "update Appeal set appeals_status = 'UNDER_CONSIDERATION' where id =:id and appeals_status = 'NEW_STATUS'")
    void setStatusUnderConsideration(@Param("id") Long id);

    /**
     * changes the appealsStatus of an appeal depending on its registrationDate field
     * @param resolutionId - id of the archived resolution
     */
    @Modifying
    @Query("UPDATE Appeal a SET a.appealsStatus = " +
            "CASE " +
            "WHEN a.registrationDate IS NOT NULL THEN model.enum_.Status.REGISTERED " +
            "ELSE model.enum_.Status.NEW_STATUS " +
            "END " +
            "WHERE a.id = (SELECT a.id FROM Appeal a WHERE a.id = " +
            "(SELECT appeal.id FROM Appeal appeal JOIN appeal.questions q JOIN Resolution r WHERE r.question = q AND r.id = :resolutionId) " +
            "AND NOT EXISTS " +
            "(SELECT r FROM Resolution r JOIN r.question q WHERE q.appeal = a AND r.archivedDate IS NULL))")
    void setAppealStatusIfLastResolutionArchived(Long resolutionId);
}

