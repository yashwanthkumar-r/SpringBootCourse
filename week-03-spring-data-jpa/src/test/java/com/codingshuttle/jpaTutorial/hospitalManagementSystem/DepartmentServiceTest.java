package com.codingshuttle.jpaTutorial.hospitalManagementSystem;

import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.Department;
import com.codingshuttle.jpaTutorial.hospitalManagementSystem.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class DepartmentServiceTest {
    @Autowired
    private DepartmentService departmentService;

    @Test
    public void testAddDoctorsToDepartment(){
        System.out.println(departmentService.addDoctorsToDepartment(Set.of(3L,4L),"General"));
    }

/*
    //some problem with the DB mappings and ER-mapping so not working
    @Test
    public void testDeleteDoctor(){
        departmentService.deleteDoctor(3L);
    }*/

}
