package com.education.entity;

import model.enums.ResolutonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Сущность представляющая собой работу с обращением гражданина
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "resolution")
public class Resolution extends BaseEntity {

    /**
     * Дата создания резолюции
     */
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    /**
     * Дата архивации резолюции
     */
    @Column(name = "archived_date")
    private ZonedDateTime archivedDate;

    /**
     * Дата последнего действия по резолюции
     */
    @Column(name = "last_action_date")
    private ZonedDateTime lastActionDate;

    /**
     * Категория - резолюция, направление или запрос
     */
    @Column(name = "resolution_type")
    @Enumerated(EnumType.STRING)
    private ResolutionType type;

    /**
     * Создатель резолюции
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Employee creator;

    /**
     * Принимающий резолюцию
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signer_id")
    private Employee signer;

    /**
     * Исполнитель резолюции
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "resolution_executor",
            joinColumns = @JoinColumn(name = "resolution_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private List<Employee> executor;

    /**
     * Куратор исполнения резолюции исполнителем
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curator_id")
    private Employee curator;

    /**
     * Серийный номер резолюции
     */
    @Column(name = "serial_number")
    private String serialNumber;



}
