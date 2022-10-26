package com.officemap.authorization.api;

import com.officemap.authorization.authorized.user.UserDto;
import org.springframework.web.reactive.function.client.WebClient;

public class UserDtoRequester {

    WebClient authorizationWebClient = WebClient.builder()
            .baseUrl("http://localhost:8091")
            .build();

    public UserDto requestUserDto(String token) {
        return authorizationWebClient.get().uri("api/ve/secured/authentication")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }
}
