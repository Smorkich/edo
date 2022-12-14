package com.education.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "employee")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mild_name")
    private String midlName;

    @Column(name = "address")
    private String address;

    @Column(name = "fio_dative")
    private String fioDative;

    @Column(name = "fio_nominative")
    private String fioNominative;

    @Column(name = "fio_Genitive")
    private String fioGenitive;

    @Column(name = "external_id")
    private long externalId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "work_phone")
    private String workPhone;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "user_name")
    private String username;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "archived-date")
    private ZonedDateTime archivedDate;

}
