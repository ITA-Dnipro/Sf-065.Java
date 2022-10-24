package com.example.employeemanagementauth.controller;

import com.example.employeemanagementauth.dto.AuthenticationResponce;
import com.example.employeemanagementauth.dto.LoginRequest;
import com.example.employeemanagementauth.model.User;
import com.example.employeemanagementauth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.OutputKeys;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/secured")
public class SecuredController {

    private final AuthService authService;

    @PostMapping("/tokenValidator")
    public ResponseEntity<String> validateRequest() {
        return new ResponseEntity<>("Token is valid", HttpStatus.OK);
    }

    @GetMapping("/authentication")
    public ResponseEntity<?> authentication() {
        User currenUser = authService.getCurrentUser();
      return  ResponseEntity.status(HttpStatus.OK).body(currenUser);
    }
}

