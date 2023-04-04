package com.education.entity;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import lombok.*;

import java.time.ZonedDateTime;

/**
 * Сущность описывающая регион
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "region")
public class Region extends BaseEntity {

    /**
     * Идентификатор региона из внешних систем
     */
    @Column(name = "external_id")
    private String externalId;

    /**
     * Название субъекта РФ
     */

    @Column(name = "name_subject_rf")
    private String nameSubjectRussianFederation;

    /**
     * Дата архивации
     */
    @Column(name = "archiving_date")
    private ZonedDateTime archivingDate;

    /**
     * Количество сотрудников
     */
    @Column(name = "quantity")
    private Integer quantity;

    /**
     * Федеральный округ(связанная сущность):
     * {Название Сайт федерального округа}
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "federal_district_id", referencedColumnName = "id")
    private FederalDistrict federalDistrict;

    /**
     * Количество первичных отделений в регионе
     */
    @Column(name = "primary_branches")
    private Integer primaryBranches;

    /**
     * Количество местных отделений в регионе
     */
    @Column(name = "local_branches")
    private Integer localBranches;
}
