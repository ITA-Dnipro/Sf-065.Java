package com.example.employeemanagementauth.dto;

import com.example.employeemanagementauth.model.Department;
import com.example.employeemanagementauth.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
public class DepartmentsDTO {
    private Long id;
    private String departmentName;
    private boolean enabled;
    List<User> employees = new ArrayList<>();
    private Department parentDepartment;
    private Set<Department> subDepartments = new HashSet<>();
}
