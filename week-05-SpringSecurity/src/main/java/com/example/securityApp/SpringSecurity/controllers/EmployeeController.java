package com.example.securityApp.SpringSecurity.controllers;

import com.example.securityApp.SpringSecurity.advice.ApiResponse;
import com.example.securityApp.SpringSecurity.client.EmployeeClient;
import com.example.securityApp.SpringSecurity.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeClient employeeClient;

    @GetMapping
    public ApiResponse<List<EmployeeDTO>> getAllEmployees() {
        return employeeClient.getAllEmployee();
    }

    @GetMapping("/{empId}")
    public ApiResponse<EmployeeDTO> getEmpById(@PathVariable("empId") Long id) {
        return employeeClient.getEmpByID(id);
    }


    @PostMapping
    public ApiResponse<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO input) {
        return employeeClient.createEmployee(input);
    }
}
