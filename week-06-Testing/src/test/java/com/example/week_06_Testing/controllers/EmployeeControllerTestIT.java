package com.example.week_06_Testing.controllers;

import com.example.week_06_Testing.dto.EmployeeDTO;
import com.example.week_06_Testing.entities.EmployeeEntity;
import com.example.week_06_Testing.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;



class EmployeeControllerTestIT extends AbstractIntegrationTest{

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeEntity testEmployee;
    private EmployeeDTO testEmployeeDTO;

    @BeforeEach
    public void setup() {
        employeeRepository.deleteAll();

        testEmployee = EmployeeEntity.builder()
                .name("Yash")
                .email("yash@123.com")
                .age(23)
                .role("Manager")
                .dateOfJoining(LocalDate.now())
                .isActive(true)
                .build();

        testEmployeeDTO = EmployeeDTO.builder()
                .name("Yash")
                .email("yash@123.com")
                .age(23)
                .role("Manager")
                .dateOfJoining(LocalDate.now())
                .isActive(true)
                .build();
    }

    @Test
    public void testGetEmployeeById() {
        EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);

        webTestClient.get()
                .uri("/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeEntity.class)
                //.isEqualTo(testEmployeeDTO)
                .value(responseEmp -> {
                    assertThat(responseEmp.getName()).isEqualTo(savedEmployee.getName());
                    assertThat(responseEmp.getEmail()).isEqualTo(savedEmployee.getEmail());
                });
    }

    @Test
    public void testGetEmployeeByIdInvalid() {

        webTestClient.get()
                .uri("/employees/{id}", 1L)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testCreateNewEmployee_whenValid() {

        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployee.getEmail())
                .jsonPath("$.name").isEqualTo(testEmployee.getName());
    }

    @Test
    public void testCreateNewEmployee_whenInvalid() {
        EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);

        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().is5xxServerError();
    }


    @Test
    public void testUpdateEmployeeByID_WhenValid() {
        EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);
        testEmployeeDTO.setName("SUrya");
        testEmployeeDTO.setSalary(300000.00);

        webTestClient.put()
                .uri("/employees/{id}", savedEmployee.getId())
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo(testEmployeeDTO.getName())
                .jsonPath("$.salary").isEqualTo(testEmployeeDTO.getSalary());
    }

    @Test
    public void testUpdateEmployeeByID_WhenInvalid() {
        webTestClient.put()
                .uri("/employees/{id}", 99999L)
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testUpdateEmployeeByID_WhenInvalidEmail() {
        EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);
        testEmployeeDTO.setName("SUrya");
        testEmployeeDTO.setEmail("surya@123.com");

        webTestClient.put()
                .uri("/employees/{id}", savedEmployee.getId())
                .bodyValue(testEmployeeDTO)
                .exchange()
                .expectStatus().is5xxServerError();

    }


    @Test
    public void testDeleteEmpByID_WhenIdNotFound(){
        webTestClient.delete()
                .uri("/employees/{id}", 22L)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testDeleteEmpByID_WhenIdIsFound(){
        EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);

        webTestClient.delete()
                .uri("/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .value(response -> assertThat(response).isTrue());
    }


}