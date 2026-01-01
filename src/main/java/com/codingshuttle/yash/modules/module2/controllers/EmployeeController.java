package com.codingshuttle.yash.modules.module2.controllers;

import com.codingshuttle.yash.modules.module2.configs.MapperConfig;
import com.codingshuttle.yash.modules.module2.dto.EmployeeDTO;
import com.codingshuttle.yash.modules.module2.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")  //parent path
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService,MapperConfig mapperConfig) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees(@RequestParam("age") Integer age, @RequestParam String sortBy) {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDTO CreateNewEmployee(@RequestBody EmployeeDTO employeeInput) {
        return employeeService.CreateNewEmployee(employeeInput);
    }

    @PutMapping
    public String UpdateEmpByID() {
        return "emplyoee details updated";
    }
}