package com.education.repository.region;

import com.education.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository в "edo-repository", служит для отправки запросов к БД
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    /**
     * Метод устанавливает дату архивации
     */
    @Modifying
    @Query(nativeQuery = true, value = "update region set archiving_date = now() where id =:id and archiving_date is null")
    void moveToArchive(@Param("id") Long id);
}
