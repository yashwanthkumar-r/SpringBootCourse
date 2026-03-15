package com.example.homework4.CurrencyConverter.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Currency")
@AllArgsConstructor
@NoArgsConstructor
public class currencyEntity {

    @Id
    private Long id;

    private String baseCurr;

    private String reqCurr;

}
