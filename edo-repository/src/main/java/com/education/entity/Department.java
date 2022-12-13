package com.education.entity;

import jakarta.persistence.*;
import static jakarta.persistence.FetchType.LAZY;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.Date;
/**
* @author Usolkin Dmitry
* Сущность Department расширяет сущность BaseEntity
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "department")
@ToString
public class Department extends BaseEntity {

    /* Поле короткое имя*/
    @Column(name = "short_name")
    private String shortName;
    /* Поле полное имя*/

    @Column(name = "full_name")
    private String fullName;
    /* Поле адрес*/

    @Column(name = "address")
    private String address;

    /* Поле с внешним идентификатором, у себя не создаем сущность,
    скачиваем из чужого хранилища)
     */
    @Column(name = "externalId")
    private long externalId;

    /* Поле с номером телефона*/
    @Column(name = "phone")
    private int phone;

    /* Поле с вышестоящим департаментом */
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "id")
    private Department department;

    /* Поле с датой создания */
    @Column(name="creation_date")
    private Date creationDate;

    /* Поле с датой архивирования */
    @Column(name="archived_date")
    private Date archivedDate;

}
