package com.education.entity;

import jakarta.persistence.*;
import lombok.*;
/**
 * Сущность описывающая адрес проживания пользователя отправившего обращение
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderMethodName = "builder_address")
@Table(name = "address")
public class Address extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Полный адрес
     */
    @Column(name = "full_address")
    private String fullAddress;

    /**
     * Название улицы
     */
    @Column(name = "street")
    private String street;

    /**
     * Дом
     */
    @Column(name = "house")
    private String house;

    /**
     * Почтовый индекс
     */
    @Column(name = "index")
    private Integer index;

    /**
     * Корпус
     */
    @Column(name = "housing")
    private String housing;

    /**
     * Строение
     */
    @Column(name = "building")
    private String building;

    /**
     * Город
     */
    @Column(name = "city")
    private String city;

    /**
     * Регион
     */
    @Column(name = "region")
    private String region;

    /**
     * Страна
     */
    @Column(name = "country")
    private String country;

    /**
     * Квартира
     */
    @Column(name = "flat")
    private String flat;
}
