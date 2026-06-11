package com.example.week_06_Testing.services;

import com.example.week_06_Testing.Exceptions.ResourceNotFoundException;
import com.example.week_06_Testing.dto.EmployeeDTO;
import com.example.week_06_Testing.entities.EmployeeEntity;
import com.example.week_06_Testing.repositories.EmployeeRepository;
import com.example.week_06_Testing.repositories.TestContainersConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.*;

@Import(TestContainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private EmployeeEntity mockEmployee;

    private EmployeeDTO mockEmployeeDto;

    @BeforeEach
    public void setup() {
        mockEmployee = EmployeeEntity.builder()
                .id(1L)
                .name("Yash")
                .email("yash@123.com")
                .age(23)
                .role("Manager")
                .dateOfJoining(LocalDate.now())
                .isActive(true)
                .build();

        mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDTO.class);
    }


    @Test
    public void testGetEmployeeById_WhenEmpIsPresent() {
        //assign
        Long id = mockEmployee.getId();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));

        //act
        EmployeeDTO dto = employeeService.getEmployeeById(id);

        //test
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getName()).isEqualTo(mockEmployee.getName());
        verify(employeeRepository, times(1)).findById(id);
    }

    @Test
    public void testGetEmployeeById_WhenEmpIsNotPresent() {
        //assign
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        //act and test
        assertThatThrownBy(() -> employeeService.getEmployeeById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee Not Found: 1");

        verify(employeeRepository).findById(1L);

    }


    @Test
    public void testCreateNewEmployee_WhenEmpIsValid() {
        //assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(null);
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(mockEmployee);

        //act
        EmployeeDTO dto = employeeService.CreateNewEmployee(mockEmployeeDto);

        //test
        assertThat(dto).isNotNull();
        assertThat(dto.getEmail()).isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository, times(1)).findByEmail(anyString());

        ArgumentCaptor<EmployeeEntity> employeeCaptor = ArgumentCaptor.forClass(EmployeeEntity.class);
        verify(employeeRepository).save(employeeCaptor.capture());

        EmployeeEntity capturedEmployee = employeeCaptor.getValue();
        assertThat(capturedEmployee.getEmail()).isEqualTo(mockEmployeeDto.getEmail());

    }

    @Test
    public void testCreateNewEmployee_WhenEmpIsInvalid() {
        //assign
        when(employeeRepository.findByEmail(mockEmployeeDto.getEmail())).thenReturn(mockEmployee);

        //act and test
        assertThatThrownBy(() -> employeeService.CreateNewEmployee(mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee with this email already exists: " + mockEmployeeDto.getEmail());

        verify(employeeRepository).findByEmail(mockEmployeeDto.getEmail());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    public void testUpdateEmployeeByID_WhenEmpIsValid() {
        //assign
        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDto.setName("Surya");
        mockEmployeeDto.setSalary(200000.00);

        EmployeeEntity emp = modelMapper.map(mockEmployeeDto, EmployeeEntity.class);
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(emp);


        //act
        EmployeeDTO dto = employeeService.updateEmployeeByID(mockEmployeeDto.getId(), mockEmployeeDto);

        //test
        assertThat(dto).isNotNull();
        assertThat(dto.getEmail()).isEqualTo(mockEmployee.getEmail());
        assertThat(dto).isEqualTo(mockEmployeeDto);

        ArgumentCaptor<EmployeeEntity> empCaptor = ArgumentCaptor.forClass(EmployeeEntity.class);
        verify(employeeRepository).save(empCaptor.capture());

        EmployeeEntity employeeEntity = empCaptor.getValue();
        assertThat(employeeEntity.getId()).isEqualTo(mockEmployeeDto.getId());
        assertThat(employeeEntity.getName()).isEqualTo("Surya");

    }

    @Test
    public void testUpdateEmployeeByID_WhenEmpIsInvalid() {
        //assign
        when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.empty());

        //act and test
        assertThatThrownBy(() -> employeeService.updateEmployeeByID(mockEmployeeDto.getId(), mockEmployeeDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee Not Found: " + mockEmployeeDto.getId());

        verify(employeeRepository).findById(mockEmployeeDto.getId());
        verify(employeeRepository, never()).save(any());

    }

    @Test
    public void testUpdateEmployeeByID_WhenUpdatingEmail() {
        //act
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDto.setEmail("surya@gmail.com");

        //act and test
        assertThatThrownBy(() -> employeeService.updateEmployeeByID(1L, mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Email cannot be updated/changed");

        verify(employeeRepository).findById(mockEmployeeDto.getId());
        verify(employeeRepository, never()).save(any());

    }

    @Test
    public void testDeleteEmpByID_WhenIdIsInvalid() {
        //assign
        when(employeeRepository.existsById(2L)).thenReturn(false);

        //act and test
        assertThatThrownBy(() -> employeeService.deleteEmpByID(2L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee Not Found: " + 2L);

        verify(employeeRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testDeleteEmpByID_WhenIdInvalid() {
        //assign
        when(employeeRepository.existsById(1L)).thenReturn(true);

        assertThatCode(() -> employeeService.deleteEmpByID(1L))
                .doesNotThrowAnyException();

        verify(employeeRepository).deleteById(1L);
    }

    @Test
    public void testPartialEmpUpdate_WhenEmpIsValid() {
        //assign
        Map<String, Object> update = new HashMap<>();
        update.put("name", "YashwanthKumar");
        update.put("age", 35);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee));

        mockEmployee.setName("YashwanthKumar");
        mockEmployee.setAge(35);
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(mockEmployee);

        //act
        EmployeeDTO dto = employeeService.partialEmpUpdate(update, 1L);

        //test
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo(mockEmployee.getName());

    }

    @Test
    public void testPartialEmpUpdate_WhenEmpIsInvalid() {
        //assign
        Map<String, Object> update = new HashMap<>();
        update.put("ename", "YashwanthKumar");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee));
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(mockEmployee);

        //act
        EmployeeDTO dto = employeeService.partialEmpUpdate(update, 1L);

        //test
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isNotEqualTo("YashwanthKumar");
    }

    @Test
    public void testGetAllEmployees() {
        //assign
        when(employeeRepository.findAll()).thenReturn(List.of(mockEmployee));

        //act
        List<EmployeeDTO> dtos = employeeService.getAllEmployees();

        //test
        assertThat(dtos).isNotNull();
        assertThat(dtos).isNotEmpty();
    }

}