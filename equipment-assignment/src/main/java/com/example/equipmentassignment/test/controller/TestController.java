package com.example.equipmentassignment.test.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;


@RestController
@RequestMapping("api/test")
public class TestController {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public TestController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    @GetMapping("getuser")
    public ResponseEntity<UserDTO> getUser(@RequestHeader (name="Authorization") String token){
        UserDTO user =  webClientBuilder.build().get()
                .uri("http://employee-management-auth:8091/api/v1/secured/authentication")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();

      return ResponseEntity.ok(user);
    }

//    WebClient webClient = WebClient.builder()
//            .baseUrl("http://localhost:8091")
//            .build();
//
//    public UserDTO getUserfrommAuthApi(String token) {
//        return webClient.get()
//                .uri("api/v1/secured/authentication")
//                .header("Authorization", token)
//                .retrieve()
//                .bodyToMono(UserDTO.class)
//                .block();
//    }



}
