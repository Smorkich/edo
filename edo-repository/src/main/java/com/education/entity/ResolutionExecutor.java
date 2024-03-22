package com.education.entity;


import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resolution_executor")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "Объект для передачи данных")
public class ResolutionExecutor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resolution_id", nullable = false)
    private Resolution resolution;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

}

