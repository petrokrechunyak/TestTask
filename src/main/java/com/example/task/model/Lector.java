package com.example.task.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    @Enumerated(EnumType.STRING)
    private Degree degree;

    private double salary;

    @ManyToMany(mappedBy = "lectors")
    private List<Department> departments;

}

