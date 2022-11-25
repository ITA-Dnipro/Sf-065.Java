package com.example.employeemanagementauth.controller;

import com.example.employeemanagementauth.dto.AuthenticationResponse;
import com.example.employeemanagementauth.dto.LoginRequest;
import com.example.employeemanagementauth.dto.RefreshTokenRequest;
import com.example.employeemanagementauth.dto.RegisterRequest;
import com.example.employeemanagementauth.service.AuthService;
import com.example.employeemanagementauth.service.RefreshtokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshtokenService refreshtokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid RegisterRequest registerRequest) {
        authService.signUp(registerRequest);
        return new ResponseEntity<>
                ("User registration successful",
                        HttpStatus.OK);
    }

    @GetMapping("/verifyaccount/{verificationToken}")
    public ResponseEntity<String> verifyAccount(@PathVariable String verificationToken) {
        authService.verifyAccount(verificationToken);
        return new ResponseEntity<>("Account is verified successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login( @Valid  @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshtokenService.deleteToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }


}
