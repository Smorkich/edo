package com.education.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Сущность электронная подпись пользователя
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "facsimile")
public class Facsimile extends BaseEntity {
    /**
     * Признак архивности
     */
    @Column(name = "isArchived")
    private Boolean isArchived;

    /**
     * Cвязь с пользователем
     */

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    /**
     * Cвязь с департаментом
     */

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    /**
     * Cвязь с файлом
     */

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filePool_id", referencedColumnName = "id")
    private FilePool filePoolId;

}
