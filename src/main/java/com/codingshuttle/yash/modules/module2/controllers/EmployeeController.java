package com.codingshuttle.yash.modules.module2.controllers;

import com.codingshuttle.yash.modules.module2.configs.MapperConfig;
import com.codingshuttle.yash.modules.module2.dto.EmployeeDTO;
import com.codingshuttle.yash.modules.module2.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")  //parent path
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService, MapperConfig mapperConfig) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam("age") Integer age, @RequestParam String sortBy) {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> CreateNewEmployee(@RequestBody @Valid EmployeeDTO employeeInput) {
        return new ResponseEntity<>(employeeService.CreateNewEmployee(employeeInput), HttpStatus.CREATED); //new ResponseEntity<>(body, status)
    }

    @PutMapping(path = "/{employeeID}")
    public ResponseEntity<EmployeeDTO> UpdateEmpByID(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long employeeID) {
        return ResponseEntity.ok(employeeService.updateEmployeeByID(employeeID, employeeDTO));
    }

    @PatchMapping(path = "/{employeeID}")
    public ResponseEntity<EmployeeDTO> partialEmpUpdate(@RequestBody Map<String, Object> update, @PathVariable Long employeeID) {
        return ResponseEntity.ok(employeeService.partialEmpUpdate(update, employeeID));
    }

    @DeleteMapping(path = "/{employeeID}")
    public ResponseEntity<Boolean> deleteEmpByID(@PathVariable Long employeeID) {
        return ResponseEntity.ok(employeeService.deleteEmpByID(employeeID));
    }
}