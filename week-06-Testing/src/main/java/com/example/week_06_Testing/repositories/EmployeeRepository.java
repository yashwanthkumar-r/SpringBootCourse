package com.example.week_06_Testing.repositories;

import com.example.week_06_Testing.dto.EmployeeDTO;
import com.example.week_06_Testing.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {

    EmployeeEntity findByEmail(String email);
}
