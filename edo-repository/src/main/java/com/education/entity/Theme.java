package com.education.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Builder
@Table(name = "theme")
public class Theme extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Название темы
     */
    @Column(name = "name")
    private String name;

    /**
     * Дата архивации
     */
    @Column(name = "archived_date")
    private Date archivedDate;

    /**
     * Дата создания
     */
    @Column(name = "creation_date")
    private Date creationDate;

    /**
     * Номер темы (код)
     */
    @Column(name = "code")
    private int code;

    /*  связка с entity Theme */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Theme parentTheme;



}

