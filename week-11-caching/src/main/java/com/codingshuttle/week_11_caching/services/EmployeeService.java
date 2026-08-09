package com.codingshuttle.week_11_caching.services;


import com.codingshuttle.week_11_caching.Exceptions.ResourceNotFoundException;
import com.codingshuttle.week_11_caching.dto.EmployeeDTO;
import com.codingshuttle.week_11_caching.entities.EmployeeEntity;
import com.codingshuttle.week_11_caching.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final String CACHE_NAME = "employees";


    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;

    }

    @Cacheable(cacheNames = CACHE_NAME, key = "#id")
    public EmployeeDTO getEmployeeById(long id) {
        log.info("fetch the employee with id: {}", id);
        EmployeeEntity employeeEntity = employeeRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.error("employee not found with id: {}", id);
                     return new ResourceNotFoundException("Employee Not Found: " + id);
                });
        log.info("successfully fetched the employee with id: {}", id);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());

    }

    @CachePut(cacheNames = CACHE_NAME, key = "#result.id")
    public EmployeeDTO CreateNewEmployee(EmployeeDTO employeeInput) {
        log.info("Create a new employee with email: {}", employeeInput.getEmail());
        EmployeeEntity employee = employeeRepository.findByEmail(employeeInput.getEmail());
        if(employee!=null){
            log.info("Employee with email already present: {}", employeeInput.getEmail());
            throw new RuntimeException("Employee with this email already exists: "+ employeeInput.getEmail());
        }
        EmployeeEntity employeeToSave = modelMapper.map(employeeInput, EmployeeEntity.class);
        EmployeeEntity employeeSaved = employeeRepository.save(employeeToSave);
        log.info("New employee saved: {}", employeeSaved.getEmail());
        return modelMapper.map(employeeSaved, EmployeeDTO.class);
    }

    //This method will update the values of employee if employeeID already exists else throw not found
    @CachePut(cacheNames = CACHE_NAME, key = "#id")
    public EmployeeDTO updateEmployeeByID(Long id, EmployeeDTO employeeDTO) {

        EmployeeEntity employeeEntity = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found: " + id));

        if(!employeeEntity.getEmail().equals(employeeDTO.getEmail())){
            log.error("Email cannot be updated/changed");
            throw new RuntimeException("Email cannot be updated/changed");
        }
        modelMapper.map(employeeDTO, employeeEntity);
        employeeEntity.setId(id);
        EmployeeEntity employeeEntitySaved = employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntitySaved, EmployeeDTO.class);
    }

    @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
    public Boolean deleteEmpByID(Long id) {
        if (!employeeRepository.existsById(id)) {
            log.error("Employee Not Found: {}",id);
            throw new ResourceNotFoundException("Employee Not Found: " + id);
        }
        employeeRepository.deleteById(id);
        log.info("Employee successfully deleted: {}",id);
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
