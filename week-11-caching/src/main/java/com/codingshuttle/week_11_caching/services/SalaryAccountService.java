package com.codingshuttle.week_11_caching.services;

import com.codingshuttle.week_11_caching.dto.SalaryAccountDto;
import com.codingshuttle.week_11_caching.entities.EmployeeEntity;
import com.codingshuttle.week_11_caching.entities.SalaryAccount;
import com.codingshuttle.week_11_caching.repositories.SalaryAccountRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SalaryAccountService {

    private final SalaryAccountRepository salaryAccountRepository;
    private final ModelMapper mapper;

    public void createAccount(EmployeeEntity employee){

        if(employee.getName().equals("Anuj")) throw new RuntimeException("Anuj is not allowed");

        SalaryAccount salaryAccount = SalaryAccount.builder()
                .employee(employee)
                .balance(BigDecimal.ZERO)
                .build();

        salaryAccountRepository.save(salaryAccount);
    }

    @Transactional
    public SalaryAccountDto updateSalary(Long accountId) {

        SalaryAccount salaryAccount = salaryAccountRepository.findById(accountId)
                .orElseThrow(()-> new RuntimeException("Account not found"));

        var currentSalary = salaryAccount.getBalance();
        var updatedSalary = currentSalary.add(BigDecimal.valueOf(1L));

        salaryAccount.setBalance(updatedSalary);
        salaryAccountRepository.save(salaryAccount);

        return new SalaryAccountDto(
                salaryAccount.getId(),
                salaryAccount.getBalance()
        );
    }
}
