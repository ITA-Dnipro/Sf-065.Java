package com.example.employeemanagementauth.service;

import com.example.employeemanagementauth.exeptions.AuthException;
import com.example.employeemanagementauth.model.Refreshtoken;
import com.example.employeemanagementauth.repository.RefreshtokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshtokenServiceImpl implements RefreshtokenService {
    private final RefreshtokenRepository refreshtokenRepository;

    @Override
    public Refreshtoken generateToken(){
        Refreshtoken refreshtoken = new Refreshtoken();
            refreshtoken.setToken(UUID.randomUUID().toString());
            refreshtoken.setCreatedDate(Instant.now());
            return refreshtokenRepository.save(refreshtoken);
    }
    @Override
    public void validateToken(String token) {
        refreshtokenRepository.findByToken(token)
                .orElseThrow(() -> new AuthException("Invalid refresh Token"));
    }

    @Override
    public void deleteToken(String token) {
        refreshtokenRepository.deleteByToken(token);
    }

}
