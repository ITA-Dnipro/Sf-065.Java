package com.officemap.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static com.officemap.common.ApplicationUrl.URL_V1;

@RestController
@RequestMapping(URL_V1)
public class AuthorizationController {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public AuthorizationController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyTokenValidity(@RequestHeader(name="Authorization") String token) {
        String response =  webClientBuilder.build().post().uri("http://employee-management-auth:8091/api/v1/secured/tokenValidator").header("Authorization", token).retrieve().bodyToMono(String.class).block();
        return ResponseEntity.ok(response);
    }
}
