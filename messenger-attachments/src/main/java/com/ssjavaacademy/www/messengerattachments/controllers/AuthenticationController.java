package com.ssjavaacademy.www.messengerattachments.controllers;

import com.ssjavaacademy.www.messengerattachments.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("api/v1")
public class AuthenticationController {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public AuthenticationController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserDto> getUser(@RequestHeader(name="Authorization") String token){
        UserDto user =  webClientBuilder.build().get()
                .uri("http://localhost:8091/api/v1/secured/authentication")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();

        return ResponseEntity.ok(user);
    }
}
