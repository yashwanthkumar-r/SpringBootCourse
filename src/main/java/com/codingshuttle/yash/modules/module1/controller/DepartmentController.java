package com.codingshuttle.yash.modules.module1.controller;

import com.codingshuttle.yash.modules.module1.dto.DepartmentDTO;
import com.codingshuttle.yash.modules.module1.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentByID(@PathVariable(name = "departmentId") Long deptID) {
        return ResponseEntity.ok(departmentService.getDepartmentByID(deptID));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment() {
        return ResponseEntity.ok(departmentService.getAllDepartment());
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createNewDepartment(@RequestBody @Valid DepartmentDTO deptInput) {
        return new ResponseEntity<>(departmentService.createNewDepartment(deptInput), HttpStatus.CREATED);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartmentByID(@RequestBody @Valid DepartmentDTO deptInput,
                                                              @PathVariable(name = "departmentId") Long deptID) {
        return ResponseEntity.ok(departmentService.updateDepartmentByID(deptInput, deptID));

    }

    @PatchMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> partialUpdateDepartmentByID(@RequestBody @Valid Map<String, Object> update, @PathVariable(name = "departmentId") Long deptID) {
        return ResponseEntity.ok(departmentService.partialUpdateDepartmentByID(update, deptID));
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable(name = "departmentId") Long deptID) {
        return ResponseEntity.ok(departmentService.deleteDepartment(deptID));
    }
}
