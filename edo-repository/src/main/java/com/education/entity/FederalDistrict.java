package com.education.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "federal_district")
public class FederalDistrict extends BaseEntity {

    /**
     * Имя региона
     */
    @Column(name = "name")
    private String name;

    /**
     * сайт региона
     */
    @Column(name = "website")
    private String website;
}
