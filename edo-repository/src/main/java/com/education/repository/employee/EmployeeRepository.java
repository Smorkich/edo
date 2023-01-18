package com.education.repository.employee;

import com.education.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author George Kiladze
 * Интерфейс который наследет JpaRepository
 * Имеет кастомные методы, используя синтаксис DATA JPA
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByIdAndArchivedDateNull(Long id);

    List<Employee> findByIdInAndArchivedDateNull(Iterable<Long> ids);

    @Modifying
    @Query(nativeQuery = true, value = "update edo.employee set archived_date = now() where id =:id and archived_date is null")
    void moveToArchived(@Param(value = "id") Long id);
}
