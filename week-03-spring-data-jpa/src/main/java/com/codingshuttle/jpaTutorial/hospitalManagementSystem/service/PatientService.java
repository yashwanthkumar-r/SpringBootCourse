package com.codingshuttle.jpaTutorial.hospitalManagementSystem.service;

import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Patient;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Transactional
    public void testPatientTransactional() {
        Patient p1 = patientRepository.findById(1L).orElseThrow();
        Patient p2 = patientRepository.findById(1L).orElseThrow();

        System.out.println(p1 + "\n" + p2);
        System.out.println(p1 == p2);
    }

    @Transactional
    public void deletePatient(Long patientId){
        patientRepository.findById(patientId).orElseThrow();
        patientRepository.deleteById(patientId);
    }
}
