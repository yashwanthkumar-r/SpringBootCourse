package com.codingshuttle.jpaTutorial.hospitalManagementSystem;

import com.codingshuttle.jpaTutorial.hospitalManagementSystem.dto.BloodGroupStats;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.dto.CPatientInfo;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.dto.IPatientInfo;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Patient;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository.PatientRepository;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Test
    public void testPatient() {
        for (Patient p : patientRepository.getAllPatientWithAppointments()) {
            System.out.println(p);
        }
    }

    @Test
    public void testProjection() {

        List<IPatientInfo> iPatientInfo = patientRepository.getAllPatientInfo();
        List<CPatientInfo> cPatientInfo = patientRepository.getAllPatientInfoConcrete();
        List<BloodGroupStats> bloodGroupStats = patientRepository.getBloodGroupStats();

        for (var p : bloodGroupStats) {
            System.out.println(p);
        }

        int updateRows = patientRepository.updatePatientNameByID("Aunj Kumar", 5L);
        System.out.println(updateRows);


    }


    @Test
    public void testTransactional() {
        patientService.testPatientTransactional();
    }
}
