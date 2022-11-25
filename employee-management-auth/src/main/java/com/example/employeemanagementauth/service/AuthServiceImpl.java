package com.example.employeemanagementauth.service;


import com.example.employeemanagementauth.dto.AuthenticationResponse;
import com.example.employeemanagementauth.dto.LoginRequest;
import com.example.employeemanagementauth.dto.RefreshTokenRequest;
import com.example.employeemanagementauth.dto.RegisterRequest;
import com.example.employeemanagementauth.exeptions.AuthException;
import com.example.employeemanagementauth.model.NotificationEmail;
import com.example.employeemanagementauth.model.User;
import com.example.employeemanagementauth.model.VerificationToken;
import com.example.employeemanagementauth.repository.UserRepository;
import com.example.employeemanagementauth.repository.VerificationTokenRepository;
import com.example.employeemanagementauth.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements  AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshtokenService refreshtokenService;

    @Override
    @Transactional
    public void signUp(RegisterRequest registerRequest) {
        User user = new User();
                user.setUsername(registerRequest.getUsername());
                user.setEmail(registerRequest.getEmail());
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
                user.setCreated(Instant.now());
                user.setEnabled(false);

        userRepository.save(user);
        String token = generateVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Please activate your account",
                user.getEmail(), "Thank you for signing up. " +
                "Please click on the below url to activate your account: " +
                "http://localhost:8091/api/v1/auth/verifyaccount/" + token));

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .build();

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Override
    public void verifyAccount(String verificationToken) {
         VerificationToken token = verificationTokenRepository.findByToken(verificationToken)
                    .orElseThrow(() -> new AuthException("The verificationtoken is invalid."));

            enableUser(token);
    }


    @Transactional
    private void enableUser(VerificationToken token) {
        String name = token.getUser().getUsername();
        User user =userRepository.findByUsername(name)
                .orElseThrow(() -> new AuthException("No user found with " + name));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshtokenService.generateToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }


    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
            refreshtokenService.validateToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    @Override
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }


    @Override
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }
}
