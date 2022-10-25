package com.example.equipmentassignment.test.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;


@RestController
@RequestMapping("api/test")
public class TestController {

    @GetMapping("getuser")
    public ResponseEntity<UserDTO> getUser(@RequestHeader (name="Authorization") String token){
        UserDTO user =  getUserfrommAuthApi(token);

      return ResponseEntity.ok(user);
    }

    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8091")
            .build();

    public UserDTO getUserfrommAuthApi(String token) {
        return webClient.get()
                .uri("api/v1/secured/authentication")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
    }



}
