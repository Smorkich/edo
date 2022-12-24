package com.education.repository.appeal;

import com.education.entity.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long> {

    /**
     * Метод ставит дату архивации
     */
    @Modifying
    @Query("update Appeal appeal set appeal.archivedDate =:archivedDate where appeal.id =:id")
    void moveToArchive(@Param("id") Long id, @Param("archivedDate") ZonedDateTime archiveDate);

    /**
     * Метод достает Appeal, у которого поле archivedDate = null
     */
    @Query("select u from Appeal u where u.archivedDate is null and u.id =:id")
    Optional<Appeal> findByIdNotArchived(@Param("id") Long id);

    /**
     * Метод, который достает всех Appeal, у которых поле archivedDate = null
     */
    @Query("from Appeal u where u.archivedDate is null")
    Collection<Appeal> findAllNotArchived();

}

