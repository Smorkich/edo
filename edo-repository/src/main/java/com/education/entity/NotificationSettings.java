package com.education.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import model.enum_.ActivationStatus;

/**
 * Сущность описывающая настройки оповещения пользователей
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "notification_settings")
public class NotificationSettings extends BaseEntity {

    /**
     * Статус активности оповещений
     * (Активирован, Не активирован)
     */
    @Column(name = "activation_status")
    @Enumerated(EnumType.STRING)
    private ActivationStatus activationOfNotification;

    /**
     * Связь с пользователем
     */
    @ManyToOne
    private Employee employee;

}
