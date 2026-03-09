package com.codingshuttle.prod_ready_features.client.impl;

import com.codingshuttle.prod_ready_features.Exception.ResourceNotFoundException;
import com.codingshuttle.prod_ready_features.advice.ApiResponse;
import com.codingshuttle.prod_ready_features.client.EmployeeClient;
import com.codingshuttle.prod_ready_features.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    public EmployeeClientImpl(RestClient restClient){
        this.restClient = restClient;
    }

    public ApiResponse<List<EmployeeDTO>> getAllEmployee(){
        try{
            return restClient.get()
                    .uri("employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<List<EmployeeDTO>>>() {
                    });
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public ApiResponse<EmployeeDTO> getEmpByID(Long id){
        try{
            return restClient.get()
                    .uri("employees/{empId}",id)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
                        System.out.println("Error occurred:" + res.getBody().readAllBytes());
                        throw new ResourceNotFoundException("couldn't find employee");
                    })
                    .body(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>(){
            });
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public ApiResponse<EmployeeDTO> createEmployee(EmployeeDTO input){
        try{
            return restClient.post()
                    .uri("employees")
                    .body(input)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<EmployeeDTO>>(){

                    });
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
