package com.officemap.authorization;

import com.officemap.repositories.AuthorizationTokensRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationTokenOperator {
    private final AuthorizationTokensRepository authorizationTokensRepository;

    public AuthorizationTokenOperator(AuthorizationTokensRepository authorizationTokensRepository) {
        this.authorizationTokensRepository = authorizationTokensRepository;
    }
    public void getTokenValue(String authorizationToken) {
        AuthorizationToken token = new AuthorizationToken();
        token.setTokenValue(authorizationToken);
        authorizationTokensRepository.save(token);
    }

    public List<AuthorizationToken> getAllTokensToList() {
        return authorizationTokensRepository.findAll().stream().toList();
    }
}
