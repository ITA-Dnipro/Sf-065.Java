package com.example.employeemanagementauth.mapper;

import com.example.employeemanagementauth.dto.DepartmentsDTO;
import com.example.employeemanagementauth.model.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentsMapper {
    Department map(DepartmentsDTO departmentsDTO);

    DepartmentsDTO mapToDTO(Department department);
}
