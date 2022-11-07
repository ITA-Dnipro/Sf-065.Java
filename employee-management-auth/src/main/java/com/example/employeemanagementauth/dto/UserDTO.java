package com.example.employeemanagementauth.dto;

import com.example.employeemanagementauth.model.Department;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String position;
    private Department department;
    private String phone;
}
