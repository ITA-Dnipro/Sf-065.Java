package com.ssjavaacademy.www.messengerattachments.services;

import com.ssjavaacademy.www.messengerattachments.dtos.UserDto;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException.isTokenEmpty;

@Service
public class UserService {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public UserService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public WebClient.ResponseSpec getRequestResponse(String token) {
       return webClientBuilder.build().get()
                .uri("http://localhost:8091/api/v1/secured/authentication")
                .header("Authorization", token)
                .retrieve();
    }

    public UserDto getUser(String token) {
        return getRequestResponse(token)
                .bodyToMono(UserDto.class)
                .block();
    }

    @PostMapping
    public void validateToken(String token) throws EmptyTokenException {
        isTokenEmpty(token);
        getRequestResponse(token).onStatus(status -> status.value() == HttpStatus.UNAUTHORIZED.value(),
                        response -> Mono.error(new EmptyTokenException("Invalid token!")))
                .bodyToMono(String.class)
                .block();
    }
}
