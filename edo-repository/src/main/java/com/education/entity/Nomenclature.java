package com.education.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/** Сущность номенклатура */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Nomenclature extends BaseEntity {

    /** Колонка даты создания */
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    /** Колонка даты архивации */
    @Column(name = "archived_date")
    private ZonedDateTime archivedDate;

    /** Шаблон записи  */
    @Column(name = "template")
    private String template;

    /** Текущее значение записи */
    @Column(name = "current_value")
    private Long currentValue;

    /** Индекс записи */
    @Column(name = "index")
    private int index;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Nomenclature nomenclature;

}
