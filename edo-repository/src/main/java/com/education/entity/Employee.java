package com.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

/**
 * @author George Kiladze
 * Сущность Employee расширяет сущность BaseEntity
 */
public class Employee extends BaseEntity {

    /**
     * firstName - Имя
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * lastName - Фамилия
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * middleName - Отчество
     */
    @Column(name = "middle_name")
    private String middleName;

    /**
     * address - Адрес
     */
    @Column(name = "address")
    private String address;

    /**
     * fioDative - ФИО в дательном падеже
     */
    @Column(name = "fio_dative")
    private String fioDative;

    /**
     * fioNominative - ФИО в именительном падеже
     */
    @Column(name = "fio_nominative")
    private String fioNominative;

    /**
     * fioGenitive - ФИО в родительном падеже
     */
    @Column(name = "fio_genitive")
    private String fioGenitive;

    /**
     * externalId - Внешний индификатор, который будем получать из чужого хранилища
     */
    @Column(name = "external_id")
    private long externalId;

    /**
     * phone - Номер телефона сотовый
     */
    @Column(name = "phone")
    private String phone;

    /**
     * workPhone - Рабочий номер телефона
     */
    @Column(name = "work_phone")
    private String workPhone;

    /**
     * birthDate - Дата рождения
     */
    @Column(name = "birth_date")
    private String birthDate;

    /**
     * username - Имя пользователя
     */
    @Column(name = "user_name")
    private String username;

    /**
     * creationDate - Дата создания
     */
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    /**
     * archivedDate - Дата архивации
     */
    @Column(name = "archived-date")
    private ZonedDateTime archivedDate;

}
