package com.codingshuttle.yash.modules.module2.services;

import com.codingshuttle.yash.modules.module2.dto.EmployeeDTO;
import com.codingshuttle.yash.modules.module2.entities.EmployeeEntity;
import com.codingshuttle.yash.modules.module2.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;


    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;

    }

    public EmployeeDTO getEmployeeById(long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());

    }

    public EmployeeDTO CreateNewEmployee(EmployeeDTO employeeInput) {
        EmployeeEntity employeeToSave = modelMapper.map(employeeInput, EmployeeEntity.class);
        EmployeeEntity employeeSaved = employeeRepository.save(employeeToSave);
        return modelMapper.map(employeeSaved, EmployeeDTO.class);
    }
}
