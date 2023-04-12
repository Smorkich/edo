package com.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Сущность описывающая перенос крайнего срок резолюции
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "deadline_resolution")

public class DeadlineResolution extends BaseEntity {

    /**
     * Крайний срок для резолюции
     */
    @JoinColumn(name = "deadline")
    private LocalDate deadline;

    /**
     * Причина переноса крайнего срока
     */
    @JoinColumn(name = "transfer_deadline")
    private String transferDeadline;

    /**
     * Содержит ссылку на резолюцию, у которой переносим срок
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolution_id", referencedColumnName = "id")
    private Resolution resolution;
}
