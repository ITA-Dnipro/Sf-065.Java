package com.example.employeemanagementauth.dto;

import com.example.employeemanagementauth.model.Department;
import com.example.employeemanagementauth.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentsDTO extends RepresentationModel {
    private Long id;
    private String departmentName;
    private boolean enabled;
//    List<User> employees = new ArrayList<>();
//    private Department parentDepartment;
//    private User departmentLead;
}
