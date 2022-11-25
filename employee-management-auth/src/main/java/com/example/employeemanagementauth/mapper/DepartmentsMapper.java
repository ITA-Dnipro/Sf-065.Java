package com.example.employeemanagementauth.mapper;

import com.example.employeemanagementauth.dto.DepartmentsDTO;
import com.example.employeemanagementauth.model.Department;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DepartmentsMapper {
    DepartmentsMapper INSTANCE = Mappers.getMapper(DepartmentsMapper.class);

    Department mapDTOtoDepartment(DepartmentsDTO departmentsDTO);


    DepartmentsDTO mapDepartmentToDTO(Department department);
}
