package com.codingshuttle.week_11_caching.dto;


import com.codingshuttle.week_11_caching.entities.EmployeeEntity;

import java.math.BigDecimal;

public record SalaryAccountDto(Long id, BigDecimal balance) {
}
