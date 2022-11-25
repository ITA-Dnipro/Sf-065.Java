package com.example.employeemanagementauth.service;

import com.example.employeemanagementauth.dto.AuthenticationResponse;
import com.example.employeemanagementauth.dto.LoginRequest;
import com.example.employeemanagementauth.dto.RefreshTokenRequest;
import com.example.employeemanagementauth.dto.RegisterRequest;
import com.example.employeemanagementauth.model.User;



public interface AuthService {
    void signUp(RegisterRequest registerRequest);
    void verifyAccount(String verificationToken);
    AuthenticationResponse login(LoginRequest loginRequest);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    boolean isLoggedIn();
    User getCurrentUser();
}
