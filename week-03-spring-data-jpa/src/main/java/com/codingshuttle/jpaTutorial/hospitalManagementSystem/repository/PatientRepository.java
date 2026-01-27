package com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository;

import com.codingshuttle.jpaTutorial.hospitalManagementSystem.dto.BloodGroupStats;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.dto.CPatientInfo;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.dto.IPatientInfo;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<IPatientInfo> findByGender(String gender);


    @Query("select p.id as id, p.name as name, p.email as email from Patient p")
    List<IPatientInfo> getAllPatientInfo();

    @Query("select new com.codingshuttle.jpaTutorial.hospitalManagementSystem.dto.CPatientInfo(p.id, p.name, p.email) " +
            "from Patient p")
    List<CPatientInfo> getAllPatientInfoConcrete();


    @Query("Select new com.codingshuttle.jpaTutorial.hospitalManagementSystem.dto.BloodGroupStats(p.bloodGroup, " +
            "COUNT(p)) from Patient p group by p.bloodGroup Order By COUNT(p)")
    List<BloodGroupStats> getBloodGroupStats();

    @Transactional
    @Modifying
    @Query("update Patient p set p.name=:name where p.id=:id")
    int updatePatientNameByID(@Param("name") String name, @Param("id") Long id);


    @Query("select p from Patient p Left Join Fetch p.appointments")
    List<Patient> getAllPatientWithAppointments();
}



