package com.ssjavaacademy.www.messengerattachments.services;

import com.ssjavaacademy.www.messengerattachments.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public UserDto getUser(@RequestHeader(name="Authorization") String token){
        UserDto user =  webClientBuilder.build().get()
                .uri("http://localhost:8091/api/v1/secured/authentication")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();

        System.out.println(user);
        return user;
    }
}
