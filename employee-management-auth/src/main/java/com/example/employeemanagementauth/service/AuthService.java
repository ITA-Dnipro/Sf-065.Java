package com.example.employeemanagementauth.service;

import com.example.employeemanagementauth.dto.AuthenticationResponce;
import com.example.employeemanagementauth.dto.LoginRequest;
import com.example.employeemanagementauth.dto.RefreshTokenRequest;
import com.example.employeemanagementauth.dto.RegisterRequest;
import com.example.employeemanagementauth.model.User;



public interface AuthService {
    void signUp(RegisterRequest registerRequest);
    void verifyAccount(String verificationToken);
    AuthenticationResponce login(LoginRequest loginRequest);
    AuthenticationResponce refreshToken(RefreshTokenRequest refreshTokenRequest);
    boolean isLoggedIn();
    User getCurrentUser();
}
