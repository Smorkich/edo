package com.education.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import model.enum_.Status;

import java.time.ZonedDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "execution_report")
public class ExecutionReport extends BaseEntity {

    /**
     * Дата создания отчета
     */
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    /**
     * Коментарий к отчету
     */
    @Column(name = "execution_comment")
    private String executionComment;

    /**
     * Статус выполнения задачи
     */
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Идентификатор исполнителя который подал отчет
     */
    @Column(name = "executor_id")
    private Long executorId;

    /**
     * Идентификтаор резолюции
     */
    @Column(name = "resolution_id")
    private Long resolutionId;
}
