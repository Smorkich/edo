package com.education.repository.department;

import com.education.entity.Department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Интерфейс который наследет JpaRepository
 * Имеет кастомные методы, используя синтаксис DATA JPA
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Modifying
    @Query("UPDATE department d  SET d.archivedDate = :date WHERE d.id = :id and d.archivedDate is null")
    void removeToArchived(@Param(value = "date") ZonedDateTime zonedDateTime, @Param(value = "id") Long id);
    Department findByIdAndArchivedDateNull(Long id);


    List<Department> findByIdInAndArchivedDateNull(Iterable<Long> ids);


}




