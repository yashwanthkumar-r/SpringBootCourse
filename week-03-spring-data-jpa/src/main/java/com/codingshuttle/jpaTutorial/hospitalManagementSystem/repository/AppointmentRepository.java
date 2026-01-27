package com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository;

import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
