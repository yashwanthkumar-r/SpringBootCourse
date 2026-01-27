package com.codingshuttle.jpaTutorial.hospitalManagementSystem.service;

import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Insurance;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Patient;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository.InsuranceRepository;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    private final PatientRepository patientRepository;

    @Transactional
    public Insurance assignInsuranceToPatient(Insurance insurance, Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        patient.setInsurance(insurance); //dirty patient

        insurance.setPatient(patient); //optional just to maintain consistency on both tables

        return insurance;
    }

    @Transactional
    public Patient removeInsuranceToPatient(Long patientID){
        Patient patient = patientRepository.findById(patientID).orElseThrow();

        patient.setInsurance(null);

        return patient;
    }


}
