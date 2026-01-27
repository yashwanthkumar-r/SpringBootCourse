package com.codingshuttle.jpaTutorial.hospitalManagementSystem;

import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Appointment;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Insurance;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.service.AppointmentService;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.service.InsuranceService;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testAssignInsuranceToPatient(){
        Insurance insurance = Insurance.builder()
                .policyNumber("UHC_123")
                .provider("UHC")
                .validUntil(LocalDate.of(2029,9,3))
                .build();

        System.out.println(insuranceService.assignInsuranceToPatient(insurance,1L));

        patientService.deletePatient(1L);
    }


    @Test
    public void testCreateNewAppointment(){
        Appointment appointment = Appointment.builder()
                .appointmentTime(LocalDateTime.of(2026,1,27,12,23))
                .reason("Head pain, pain at the abdominal")
                .status("Good")
                .build();


        System.out.println(appointmentService.createNewAppointment(appointment,1L,2L));

        patientService.deletePatient(1L);
    }

}
