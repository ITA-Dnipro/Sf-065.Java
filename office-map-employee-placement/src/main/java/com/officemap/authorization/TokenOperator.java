package com.officemap.authorization;

import com.officemap.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenOperator {
    private final TokenRepository tokenRepository;

    public TokenOperator(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void getToken(String authorizationToken) {
        Token newToken = new Token();
        newToken.setTokenValue(authorizationToken);
        tokenRepository.save(newToken);
    }

    public List<Token> getTokensList() {
        return tokenRepository.findAll().stream().toList();
    }
}
