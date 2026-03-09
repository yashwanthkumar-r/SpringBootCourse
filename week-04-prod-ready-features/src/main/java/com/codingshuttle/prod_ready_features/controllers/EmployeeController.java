package com.codingshuttle.prod_ready_features.controllers;

import com.codingshuttle.prod_ready_features.advice.ApiResponse;
import com.codingshuttle.prod_ready_features.client.EmployeeClient;
import com.codingshuttle.prod_ready_features.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeClient employeeClient;

    @GetMapping
    public ApiResponse<List<EmployeeDTO>> getAllEmployees(){
        return employeeClient.getAllEmployee();
    }

    @GetMapping("/{empId}")
    public ApiResponse<EmployeeDTO> getEmpById(@PathVariable("empId") Long id){
        return employeeClient.getEmpByID(id);
    }


    @PostMapping
    public ApiResponse<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO input){
        return employeeClient.createEmployee(input);
    }
}
