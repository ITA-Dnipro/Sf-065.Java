package com.example.employeemanagementauth.controller;


import com.example.employeemanagementauth.dto.DepartmentRequest;
import com.example.employeemanagementauth.dto.DepartmentsDTO;
import com.example.employeemanagementauth.dto.UserDTO;
import com.example.employeemanagementauth.dto.UserRequest;
import com.example.employeemanagementauth.model.Department;
import com.example.employeemanagementauth.service.DepartmentsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/departments")
public class DepartmentsController {


    private final DepartmentsService departmentsService;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentsService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentsDTO> getAllDetails(@PathVariable("id") Long id) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(departmentsService.getAllDetails(id));
    }

    @PostMapping
    public ResponseEntity<DepartmentsDTO> createDepartment(@RequestBody DepartmentRequest departmentRequest ) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(departmentsService.createDepartment(departmentRequest));
    }

    @PostMapping("{name}")
    public ResponseEntity<DepartmentsDTO> createsubDepartment(@PathVariable String name, @RequestBody DepartmentRequest departmentRequest ) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(departmentsService.createsubDepartment(name, departmentRequest));
    }

    @PostMapping("{departmentId}/{username}")
    public ResponseEntity<UserDTO> assignUserToDepartment(@PathVariable Long departmentId, @PathVariable String username ) {
        return  ResponseEntity.status(HttpStatus.OK)
                .body(departmentsService.assignUserTodepartment(departmentId, username));
    }



    @GetMapping("/{id}/siblings")
    public ResponseEntity<Set<DepartmentsDTO>> getAllSiblings(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(departmentsService.getAllSiblings(id));
    }

}
