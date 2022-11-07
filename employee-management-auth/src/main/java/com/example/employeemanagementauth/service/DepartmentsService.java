package com.example.employeemanagementauth.service;

import com.example.employeemanagementauth.dto.DepartmentRequest;
import com.example.employeemanagementauth.dto.DepartmentsDTO;
import com.example.employeemanagementauth.dto.UserDTO;
import com.example.employeemanagementauth.dto.UserRequest;
import com.example.employeemanagementauth.model.Department;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;


public interface DepartmentsService {

    List<Department> getAllDepartments();

    DepartmentsDTO getAllDetails(Long id);

    Set<DepartmentsDTO> getAllSiblings( Long id);

    @Transactional
    DepartmentsDTO createDepartment(DepartmentRequest departmentRequest);
    @Transactional
    DepartmentsDTO  createsubDepartment(String name, DepartmentRequest departmentRequest);

    @Transactional
    UserDTO assignUserTodepartment(Long departmentId, String username);
}
