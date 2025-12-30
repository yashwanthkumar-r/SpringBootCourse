package com.codingshuttle.yash.modules.module2.controllers;

import com.codingshuttle.yash.modules.module2.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/employees")  //parent path
public class EmployeeEntity {

    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable(name= "employeeId") long id){
        return new EmployeeDTO("yash",id,"yash@123",26, LocalDate.of(2022,8,5),true);
    }

    @GetMapping
    public String getAllEmployees(@RequestParam("age") Integer age, @RequestParam String sortBy){
        return "Employee age: " + age + ". SortBy: "+sortBy;
    }

    @PostMapping
    public EmployeeDTO CreateNewEmployee(@RequestBody EmployeeDTO employeeInput){
        employeeInput.setId(102L);
        return employeeInput;
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