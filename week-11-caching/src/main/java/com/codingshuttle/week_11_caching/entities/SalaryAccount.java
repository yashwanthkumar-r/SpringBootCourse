package com.codingshuttle.week_11_caching.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SalaryAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal balance;

    @OneToOne(fetch = FetchType.LAZY)
    private EmployeeEntity employee;

    @Version
    private Long version;
}
