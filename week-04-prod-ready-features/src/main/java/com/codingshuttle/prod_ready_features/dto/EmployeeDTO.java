package com.codingshuttle.prod_ready_features.dto;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDTO {

    private Long id;

    private String name;

    private String email;

    private String role; //USER, ADMIN

    private Integer age;

    private Double salary;

    private LocalDate dateOfJoining;

    private Boolean isActive;
}


