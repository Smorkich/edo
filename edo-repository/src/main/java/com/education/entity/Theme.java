package com.education.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import model.dto.ThemeDto;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * @author AlexeySpiridonov
 * Сущность описывающая тему обращения граждан
 */

@Entity(name = "theme")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "theme")
public class Theme extends BaseEntity {


    /**
     * Название темы
     */
    @Column(name = "name")
    private String name;

    /**
     *  Дата архивации
     */
    @Column(name = "archived_date")
    private ZonedDateTime archivedDate;

    /**
     * Дата создания
     */
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    /**
     * Номер темы (код)
     */
    @Column(name = "code")
    private String code;

    /**
     *  связка с entity Theme
    * */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_theme")
    private Theme parentTheme;


}