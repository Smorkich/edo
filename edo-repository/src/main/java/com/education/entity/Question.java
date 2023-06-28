package com.education.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import model.enum_.Status;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author Nadezhda Pupina
 * Class Question отвечает за вопросы граждан, расширяет сущность BaseEntity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@JsonInclude(NON_NULL)
@Table(name = "question")
public class Question extends BaseEntity {

    /**
     * Дата создания вопроса
     */
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    /**
     * Дата архивации вопроса
     */
    @Column(name = "archived_date")
    private ZonedDateTime archivedDate;

    /**
     * Описание вопроса
     */
    @Column(name = "summary", nullable = false)
    private String summary;

    /**
     * Тема
     * Статус вопроса
     */
    @Column(name = "question_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Тема вопроса
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    private Theme theme;

    /**
     * Связующее поле с таблицей Appeal (с обращением)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appeal_id")
    private Appeal appeal;

}
