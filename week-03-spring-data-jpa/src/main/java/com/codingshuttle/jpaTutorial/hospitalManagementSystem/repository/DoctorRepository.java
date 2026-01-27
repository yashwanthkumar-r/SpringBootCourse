package com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository;

import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
