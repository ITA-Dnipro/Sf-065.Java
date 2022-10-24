package com.officemap.authorization;

import com.officemap.repository.AuthorizationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationTokenOperator {
    private final AuthorizationTokenRepository authorizationTokenRepository;

    public AuthorizationTokenOperator(AuthorizationTokenRepository authorizationTokenRepository) {
        this.authorizationTokenRepository = authorizationTokenRepository;
    }

    public void getToken(String authorizationToken) {
        AuthorizationToken newAuthorizationToken = new AuthorizationToken();
        newAuthorizationToken.setTokenValue(authorizationToken);
        authorizationTokenRepository.save(newAuthorizationToken);
    }

    public List<AuthorizationToken> getTokensList() {
        return authorizationTokenRepository.findAll().stream().toList();
    }
}
