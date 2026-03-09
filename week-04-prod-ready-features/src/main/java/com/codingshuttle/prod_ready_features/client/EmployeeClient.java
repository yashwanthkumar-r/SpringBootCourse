package com.codingshuttle.prod_ready_features.client;

import com.codingshuttle.prod_ready_features.advice.ApiResponse;
import com.codingshuttle.prod_ready_features.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeClient {

    ApiResponse<List<EmployeeDTO>> getAllEmployee();
    ApiResponse<EmployeeDTO> getEmpByID(Long id);

    ApiResponse<EmployeeDTO> createEmployee(EmployeeDTO input);
}
