package com.education.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import model.enum_.MemberType;

import java.time.ZonedDateTime;

/**
 * Сущность представляющая собой участников или подписантов согласования
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "member")
public class Member extends BaseEntity {

    /**
     * Тип участника согласования
     */
    @Column(name = "member_type")
    @Enumerated(EnumType.STRING)
    private MemberType type;

    /**
     * Дата создания резолюции
     */
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    /**
     * Дата, до которой должно быть исполнено
     */
    @Column(name = "execution_date")
    private ZonedDateTime executionDate;

    /**
     * Дата получения
     */
    @Column(name = "date_of_receiving")
    private ZonedDateTime dateOfReceiving;

    /**
     * Дата завершения действия
     */
    @Column(name = "end_date")
    private ZonedDateTime endDate;

    /**
     * Блок листа согласования в котором находится участник
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_block_id")
    private ApprovalBlock approvalBlock;

    /**
     * Номер по порядку согласования и порядку отображения на UI
     */
    @Column(name = "ordinal_number")
    private int ordinalNumber;

    /**
     * Сотрудник
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
