package com.example.securityApp.SpringSecurity.client;

import com.example.securityApp.SpringSecurity.advice.ApiResponse;
import com.example.securityApp.SpringSecurity.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeClient {

    ApiResponse<List<EmployeeDTO>> getAllEmployee();

    ApiResponse<EmployeeDTO> getEmpByID(Long id);

    ApiResponse<EmployeeDTO> createEmployee(EmployeeDTO input);
}
