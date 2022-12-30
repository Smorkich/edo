package com.education.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Сущность описывающая адрес проживания пользователя отправившего обращение
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "builderAddress")
@Table(name = "address")
public class Address extends BaseEntity{

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
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "latitude")
    private String latitude;
}
