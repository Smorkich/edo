package com.education.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;

/**
 * Сущность, описывающая обращение гражданина
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "appeal")
public class Appeal extends BaseEntity {

    /**
     * Дата создания обращения
     */
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    /**
     * Дата отработанного обращения
     */
    @Column(name = "archived_date")
    private ZonedDateTime archivedDate;

    /**
     * Номер обращения
     */
    @Column(name = "number")
    private String number;

    /**
     * Заголовок обращения
     */
    @Column(name = "annotation")
    private String annotation;

    /**
     * Обработчик обращения
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "appeal_signer",
            joinColumns = @JoinColumn(name = "appeal_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private Collection<Employee> signer = new HashSet<>();

    /**
     * Создатель обращения
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Employee creator;

    /**
     * Адрес обращения
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "appeal_addressee",
            joinColumns = @JoinColumn(name = "appeal_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private Collection<Employee> addressee = new HashSet<>();
}