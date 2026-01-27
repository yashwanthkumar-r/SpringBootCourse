package com.codingshuttle.jpaTutorial.hospitalManagementSystem.service;

import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Department;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Doctor;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository.DepartmentRepository;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional
    public Department addDoctorsToDepartment(Set<Long> doctorID, String departmentName){
        Department department = departmentRepository.findByName(departmentName);

        for (var id: doctorID) {
            Doctor doctor = doctorRepository.findById(id).orElseThrow();

            department.getDoctors().add(doctor);

        }
        return department;
    }

    @Transactional
    public void deleteDoctor(Long doctorID){
       Doctor doctor = doctorRepository.findById(doctorID).orElseThrow();
        List<Department> department = departmentRepository.findDepartmentsByDoctorId(doctorID);

        for(Department department1: department){
            department1.getDoctors().remove(doctor);
            departmentRepository.save(department1);
        }

        doctorRepository.delete(doctor);

    }
}
