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
    @Query("UPDATE Employee e SET e.archivedDate = :date WHERE e.id = :id and e.archivedDate is null")
    void moveToArchived(@Param(value = "date") ZonedDateTime zonedDateTime, @Param(value = "id") Long id);

    @Modifying
    @Query(nativeQuery = true, value = "SELECT * FROM employee e WHERE e.fio_nominative LIKE CONCAT(:fullName, '%') ORDER BY e.last_name LIMIT 7")
    List<Employee> findAllByFullName(@Param(value = "fullName") String fullName);
}
