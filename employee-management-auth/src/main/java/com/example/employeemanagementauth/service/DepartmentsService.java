package com.example.employeemanagementauth.service;

import com.example.employeemanagementauth.dto.DepartmentRequest;
import com.example.employeemanagementauth.dto.DepartmentsDTO;
import com.example.employeemanagementauth.dto.UserDTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;


public interface DepartmentsService {

    List<DepartmentsDTO> getAllDepartments();

    DepartmentsDTO getDepartment(Long id);

    public List<DepartmentsDTO> findDepartmentsByParent(Long parentId);

    @Transactional
    DepartmentsDTO createDepartment(DepartmentRequest departmentRequest);

    @Transactional
    DepartmentsDTO assignUserTodepartment(Long departmentId, String username);
}
