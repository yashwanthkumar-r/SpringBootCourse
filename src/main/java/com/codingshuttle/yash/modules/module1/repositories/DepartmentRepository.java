package com.codingshuttle.yash.modules.module1.repositories;

import com.codingshuttle.yash.modules.module1.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
