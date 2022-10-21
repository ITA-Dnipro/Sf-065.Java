package com.example.employeemanagementauth.service;

import com.example.employeemanagementauth.model.Refreshtoken;


public interface RefreshtokenService {
    Refreshtoken generateToken();

    void validateToken(String token);

    void deleteToken(String token);
}
