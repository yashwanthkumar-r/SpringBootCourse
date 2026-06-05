package com.example.week_06_Testing.repositories;

import com.example.week_06_Testing.entities.EmployeeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestContainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeEntity employee;

    @BeforeEach
    void setUp(){
        employee = EmployeeEntity.builder()
                .name("Yash")
                .email("yash@123.com")
                .age(23)
                .role("Manager")
                .dateOfJoining(LocalDate.now())
                .isActive(true)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee(){
        //Given
        employeeRepository.save(employee);

        //Act
        EmployeeEntity employeeEntity = employeeRepository.findByEmail(employee.getEmail());

        //Assert
        assertThat(employeeEntity).isNotNull();
        assertThat(employeeEntity.getEmail()).isEqualTo(employee.getEmail());
    }


    @Test
    void testFindByEmail_whenEmailIsNotValid_thenReturnEmptyEmployee(){
        //given

        //Act
        EmployeeEntity employeeEntity = employeeRepository.findByEmail("yash@hmm.com");

        //Assert
        assertThat(employeeEntity).isNull();
    }
}
