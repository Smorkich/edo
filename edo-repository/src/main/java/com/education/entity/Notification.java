package com.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class Notification extends BaseEntity {
    private Type type;
    @OneToOne(mappedBy = "notification")
    private Employee employee ;
    public enum Type {
        EMAIL,
        PHONE,
        INTERNAL
    }
}
