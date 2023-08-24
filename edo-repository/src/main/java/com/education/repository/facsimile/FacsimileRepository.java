package com.education.repository.facsimile;

import com.education.entity.Facsimile;
import model.dto.FacsimileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FacsimileRepository extends JpaRepository<Facsimile,Long> {

    FacsimileDto save(FacsimileDto facsimileDto);

    @Override
    @Query("select a from Facsimile a " +
            "left join fetch a.department " +
            "left join fetch a.employee " +
            "left join fetch a.filePoolId " +
            " where a.id = :id")
    Optional<Facsimile> findById(@Param("id") Long id);
}
