package com.example.employeemanagementauth.controller;

import com.example.employeemanagementauth.dto.AuthenticationResponce;
import com.example.employeemanagementauth.dto.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/secured")
public class SecuredController {

    @PostMapping("/tokenValidator")
    public ResponseEntity<String> validateRequest() {
        return new ResponseEntity<>("Token is valid", HttpStatus.OK);
    }
}
