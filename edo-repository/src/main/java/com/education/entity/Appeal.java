package com.education.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность, описывающая обращение гражданина
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "appeal")
public class Appeal extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Дата создания обращения
     */
    @Column(name = "creation_date")
    private Date creationDate;

    /**
     * Дата отработанного обращения
     */
    @Column(name = "archived_date")
    private Date archivedDate;

    /**
     * Номер обращения
     */
    @Column(name = "number")
    private int number;

    /**
     * Заголовок обращения
     */
    @Column(name = "annotation")
    private String annotation;

    /**
     * Обработчик обращения
     */
    @Column(name = "signer")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "appeal_signer",
            joinColumns = @JoinColumn(name = "appeal_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private Set<Employee> signer = new HashSet<>();

    /**
     * Автор обращения
     */
    @Column(name = "creator")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Employee creator;

    /**
     * Адрес обращения
     */
    @Column(name = "addressee")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "appeal_addressee",
            joinColumns = @JoinColumn(name = "appeal_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private Set<Employee> addressee = new HashSet<>();

}

