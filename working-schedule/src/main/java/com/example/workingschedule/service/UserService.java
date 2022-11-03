package com.example.workingschedule.service;

import com.example.workingschedule.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserService {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public UserService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    public UserDTO getUser(@RequestHeader(name = "Authorization") String token) {
        UserDTO user = webClientBuilder.build().get()
                .uri("http://localhost:8091/api/v1/secured/authentication")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();

        return user;
    }
}
