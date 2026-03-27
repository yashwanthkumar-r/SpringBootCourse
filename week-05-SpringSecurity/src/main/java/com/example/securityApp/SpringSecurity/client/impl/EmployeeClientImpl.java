package com.example.securityApp.SpringSecurity.client.impl;

import com.example.securityApp.SpringSecurity.Exception.ResourceNotFoundException;
import com.example.securityApp.SpringSecurity.advice.ApiResponse;
import com.example.securityApp.SpringSecurity.client.EmployeeClient;
import com.example.securityApp.SpringSecurity.dto.EmployeeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

    public EmployeeClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    public ApiResponse<List<EmployeeDTO>> getAllEmployee() {
        log.info("Entered into getAllEmployee, trying retrieve response");
        try {
            ApiResponse<List<EmployeeDTO>> response = restClient.get()
                    .uri("employees")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error("Couldn't get all employees data from client: {}", new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("couldn't find employees");
                    })
                    .body(new ParameterizedTypeReference<ApiResponse<List<EmployeeDTO>>>() {
                    });
            log.trace("response successfully retrieved at getAllEmployees: {}", response.toString());
            return response;
        } catch (Exception e) {
            log.error("Exception occurred at getAllEmployees: ", e);
            throw new RuntimeException(e);
        }
    }

    public ApiResponse<EmployeeDTO> getEmpByID(Long id) {
        log.info("Entered into getEmpByID, trying retrieve response for ID: {}", id);
        try {
            return restClient.get()
                    .uri("employees/{empId}", id)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error("Couldn't get emp data from client: {}", new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("couldn't find employee");
                    })
                    .body(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>() {
                    });
        } catch (Exception e) {
            log.error("Exception occurred at getEmpById: ", e);
            throw new RuntimeException(e);
        }
    }


    public ApiResponse<EmployeeDTO> createEmployee(EmployeeDTO input) {
        log.info("Entered into createEmployee: {}", input);
        try {
            return restClient.post()
                    .uri("employees")
                    .body(input)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error("Couldn't create emp data into client: {}", new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("couldn't create employee");
                    })
                    .body(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>() {

                    });
        } catch (Exception e) {
            log.error("Exception occurred at createEmployee: ", e);
            throw new RuntimeException(e);
        }
    }
}
