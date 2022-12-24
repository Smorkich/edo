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

    @Modifying
    @Query("update Appeal appeal set appeal.archivedDate =:archivedDate where appeal.id =:id")
    void moveToArchive(@Param("id") Long id, @Param("archivedDate") ZonedDateTime archiveDate);

    @Query("select u from Appeal u where u.archivedDate is null and u.id =:id")
    Optional<Appeal> findByIdNotArchived(@Param("id") Long id);

    @Query("select u from Appeal u where u.archivedDate not in ?1")
    Collection<Appeal> findAllByIdNotArchived(Iterable<Long> idList);

}

