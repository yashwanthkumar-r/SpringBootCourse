package com.example.week_06_Testing.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "department")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Boolean isActive;
}
