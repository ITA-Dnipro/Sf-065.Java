package com.example.workingschedule.service;

import com.example.workingschedule.dto.UserDTO;
import com.example.workingschedule.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    public String validateUser(String token) throws InvalidTokenException  {
        return webClientBuilder.build().get()


                .uri("http://localhost:8091/api/v1/secured/authentication")
                .header("Authorization", token)
                .retrieve()
                .onStatus(status -> status.value() == HttpStatus.UNAUTHORIZED.value(),
                 response -> Mono.error(new InvalidTokenException("Invalid token")))
                .bodyToMono(String.class)
                .block();


    }
}
