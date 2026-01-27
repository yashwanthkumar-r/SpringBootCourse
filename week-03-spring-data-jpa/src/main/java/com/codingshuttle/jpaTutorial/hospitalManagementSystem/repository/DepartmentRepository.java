package com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository;

import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    public Department findByName(String name);

    @Query("select d from Department d join d.doctors doc where doc.id = :doctorId")
    List<Department> findDepartmentsByDoctorId(@Param("doctorId") Long doctorId);

}
