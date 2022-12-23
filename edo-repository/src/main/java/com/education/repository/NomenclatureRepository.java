package com.education.repository;

import com.education.entity.Nomenclature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface NomenclatureRepository extends JpaRepository<Nomenclature,Long> {

    @Modifying
    @Query(value = "update Nomenclature nomenclature set  nomenclature.archivedDate =:archDate where nomenclature.id =:id")
    void moveToArchive( @Param("archDate") ZonedDateTime date, @Param("id") Long id);

    @Query("select u from Nomenclature u where ((u.archivedDate is null) and u.id =:id)")
    Optional<Nomenclature> findByIdNotArchived(@Param("id") Long id);

    @Query("select u from Nomenclature u where (u.archivedDate is null and u.id in :idList)")
    List<Nomenclature> findAllByIdNotArchived(@Param("idList") Collection<Long> idList);

}
