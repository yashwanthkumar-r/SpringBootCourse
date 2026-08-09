package com.codingshuttle.week_11_caching.repositories;

import com.codingshuttle.week_11_caching.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {

    EmployeeEntity findByEmail(String email);
}
