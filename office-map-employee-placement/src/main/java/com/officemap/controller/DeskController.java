package com.officemap.controller;

import com.officemap.authorization.AuthorizationToken;
import com.officemap.authorization.AuthorizationTokenOperator;
import com.officemap.authorization.api.UserDtoRequester;
import com.officemap.authorization.authorized.user.UserDto;
import com.officemap.entity.Desk;
import com.officemap.service.DeskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/desk")
public class DeskController {

    private final DeskService deskService;
    private final AuthorizationTokenOperator authorizationTokenOperator;
    private final UserDtoRequester userDtoRequester;

    public DeskController(DeskService deskService, AuthorizationTokenOperator authorizationTokenOperator, UserDtoRequester userDtoRequester) {
        this.deskService = deskService;
        this.authorizationTokenOperator = authorizationTokenOperator;
        this.userDtoRequester = userDtoRequester;
    }

    /* Should get user ID, and save it in token field in DB table "tokens" */
    @GetMapping
    public Object getAllDesks(@RequestHeader("Authorization") String authorizationToken) {
        UserDto userDto;
        try {
            userDto = userDtoRequester.requestUserDto(authorizationToken);
            authorizationTokenOperator.getToken(String.valueOf(userDto.getId()));
            return deskService.getAllDesksToList();
        } catch (Error er) {
            return "error 401..."; //ToDo
        }
    }

    @PostMapping
    public Desk addNewDesk(@RequestHeader("Authorization") String authorizationToken,
                           @RequestBody Desk deskToAdd) {
        authorizationTokenOperator.getToken(authorizationToken);
        return deskService.createNewDesk(deskToAdd);
    }

    @DeleteMapping("/{id}")
    public boolean deleteDeskById(@RequestHeader("Authorization") String authorizationToken,
                                  @PathVariable("id") Long deskId) {
        authorizationTokenOperator.getToken(authorizationToken);
        return deskService.deleteDeskById(deskId);
    }

    @GetMapping("/tokens")
    public List<AuthorizationToken> getAllTokens() {
        return authorizationTokenOperator.getTokensList();
    }
}
