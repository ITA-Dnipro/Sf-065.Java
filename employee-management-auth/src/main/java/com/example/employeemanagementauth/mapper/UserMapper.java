package com.example.employeemanagementauth.mapper;


import com.example.employeemanagementauth.dto.DepartmentsDTO;
import com.example.employeemanagementauth.dto.UserDTO;
import com.example.employeemanagementauth.model.Department;
import com.example.employeemanagementauth.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapDTOtoUser(UserDTO userDTO);

    UserDTO mapUserToDTO(User user);
}
