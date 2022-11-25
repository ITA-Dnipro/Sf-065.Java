package com.example.employeemanagementauth.controller;

import com.example.employeemanagementauth.dto.UserDTO;
import com.example.employeemanagementauth.model.User;
import com.example.employeemanagementauth.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/secured")
@SecurityRequirement(name = "Bearer Authentication")
public class SecuredController {

    private final AuthService authService;

    @PostMapping("/tokenValidator")
    public ResponseEntity<String> validateRequest() {
        return new ResponseEntity<>("Token is valid", HttpStatus.OK);
    }

    @GetMapping("/authentication")
    public ResponseEntity<User> authentication() {
        User currentUser = authService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.OK).body(currentUser);

    }
}

