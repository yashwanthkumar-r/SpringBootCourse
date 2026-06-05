package com.example.week_06_Testing.services;

import com.example.week_06_Testing.Exceptions.ResourceNotFoundException;
import com.example.week_06_Testing.dto.EmployeeDTO;
import com.example.week_06_Testing.entities.EmployeeEntity;
import com.example.week_06_Testing.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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
        EmployeeEntity employeeEntity = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found: " + id));
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
        EmployeeEntity employee = employeeRepository.findByEmail(employeeInput.getEmail());
        if(employee!=null){
            throw new RuntimeException("Employee with this email already exists: "+ employeeInput.getEmail());
        }
        EmployeeEntity employeeToSave = modelMapper.map(employeeInput, EmployeeEntity.class);
        EmployeeEntity employeeSaved = employeeRepository.save(employeeToSave);
        return modelMapper.map(employeeSaved, EmployeeDTO.class);
    }

    //This method will update the values of employee if employeeID already exists else throw not found
    public EmployeeDTO updateEmployeeByID(Long id, EmployeeDTO employeeDTO) {

        EmployeeEntity employeeEntity = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found: " + id));
        modelMapper.map(employeeDTO, employeeEntity);
        employeeEntity.setId(id);
        EmployeeEntity employeeEntitySaved = employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntitySaved, EmployeeDTO.class);
    }

    public Boolean deleteEmpByID(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee Not Found: " + id);
        }
        employeeRepository.deleteById(id);
        return true;
    }

    public EmployeeDTO partialEmpUpdate(Map<String, Object> update, Long employeeID) {
        EmployeeEntity employeeEntity = employeeRepository
                .findById(employeeID)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found: " + employeeID));


        update.forEach((field, value) -> {
            Field fieldToUpdate = ReflectionUtils.findField(EmployeeEntity.class, field);
            if (fieldToUpdate != null) {
                fieldToUpdate.setAccessible(true);
                ReflectionUtils.setField(fieldToUpdate, employeeEntity, value);
            }
        });

        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
