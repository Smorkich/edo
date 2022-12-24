package com.education.repository.department;

import com.education.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
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
    Department findByIdAndArchivedDateNull(Long id);

    //    @Query("SELECT d FROM department d WHERE d.id IN :ids AND d.archivedDate is null ")
    List<Department> findByIdInAndArchivedDateNull(Iterable<Long> ids);


}




