package com.codingshuttle.yash.modules.module2.controllers;

import com.codingshuttle.yash.modules.module2.dto.EmployeeDTO;
import com.codingshuttle.yash.modules.module2.entities.EmployeeEntity;
import com.codingshuttle.yash.modules.module2.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employees")  //parent path
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/{employeeId}")
    public EmployeeEntity getEmployeeById(@PathVariable(name= "employeeId") long id){
        return employeeRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<EmployeeEntity> getAllEmployees(@RequestParam("age") Integer age, @RequestParam String sortBy){
        return employeeRepository.findAll();
    }

    @PostMapping
    public EmployeeEntity CreateNewEmployee(@RequestBody EmployeeEntity employeeInput){
        return employeeRepository.save(employeeInput);
    }

    @PutMapping
    public String UpdateEmpByID(){
        return "emplyoee details updated";
    }
}

/*    @GetMapping(path = "/getSecretMessage")
    public String getSecretMsg(){
        return "hello dude, you can do it!!";
    }*/