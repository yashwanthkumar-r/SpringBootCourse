package com.codingshuttle.jpaTutorial.hospitalManagementSystem.service;

import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Appointment;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Doctor;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Patient;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository.AppointmentRepository;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository.DoctorRepository;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public Appointment createNewAppointment(Appointment appointment, Long patientID, Long doctorID){
        Doctor doctor = doctorRepository.findById(doctorID).orElseThrow();
        Patient patient = patientRepository.findById(patientID).orElseThrow();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        appointmentRepository.save(appointment);

        return appointment;
    }
}
