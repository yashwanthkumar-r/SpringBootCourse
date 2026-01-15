package com.codingshuttle.yash.modules.module1.services;

import com.codingshuttle.yash.modules.module1.Exceptions.ResourceNotFoundException;
import com.codingshuttle.yash.modules.module1.dto.DepartmentDTO;
import com.codingshuttle.yash.modules.module1.entity.DepartmentEntity;
import com.codingshuttle.yash.modules.module1.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    public DepartmentDTO getDepartmentByID(Long deptID) {
        DepartmentEntity entity = departmentRepository.findById(deptID)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found ID: " + deptID));
        return modelMapper.map(entity, DepartmentDTO.class);
    }

    public List<DepartmentDTO> getAllDepartment() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public DepartmentDTO createNewDepartment(DepartmentDTO deptInput) {
        DepartmentEntity departmentToSave = modelMapper.map(deptInput, DepartmentEntity.class);
        return modelMapper.map(departmentRepository.save(departmentToSave), DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartmentByID(DepartmentDTO deptInput, Long deptID) {
        DepartmentEntity departmentEntity = departmentRepository.findById(deptID)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found ID: " + deptID));
        modelMapper.map(deptInput, departmentEntity);
        departmentEntity.setId(deptID);
        return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDTO.class);
    }

    public DepartmentDTO partialUpdateDepartmentByID(Map<String, Object> update, Long deptID) {
        DepartmentEntity departmentEntity = departmentRepository.findById(deptID)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found ID: " + deptID));

        update.forEach((field, value) -> {
            Field fieldToUpdate = ReflectionUtils.findField(DepartmentEntity.class, field);
            if (fieldToUpdate != null) {
                fieldToUpdate.setAccessible(true);
                ReflectionUtils.setField(fieldToUpdate, departmentEntity, value);
            }
        });

        return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDTO.class);
    }

    public Boolean deleteDepartment(Long deptID) {
        if (departmentRepository.existsById(deptID)) {
            departmentRepository.deleteById(deptID);
            return true;
        }
        return false;
    }
}
