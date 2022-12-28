package com.education.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@Builder
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
