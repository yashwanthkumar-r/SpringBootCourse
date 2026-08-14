package com.codingshuttle.week_11_caching.repositories;

import com.codingshuttle.week_11_caching.entities.SalaryAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface SalaryAccountRepository extends JpaRepository<SalaryAccount, Long> {

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<SalaryAccount> findById(Long id);
}
