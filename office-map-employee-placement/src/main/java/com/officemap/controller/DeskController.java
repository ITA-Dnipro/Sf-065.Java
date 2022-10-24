package com.officemap.controller;

import com.officemap.authorization.AuthorizationToken;
import com.officemap.authorization.AuthorizationTokenOperator;
import com.officemap.entity.Desk;
import com.officemap.service.DeskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/desk")
public class DeskController {

    private final DeskService deskService;
    private final AuthorizationTokenOperator authorizationTokenOperator;

    public DeskController(DeskService deskService, AuthorizationTokenOperator authorizationTokenOperator) {
        this.deskService = deskService;
        this.authorizationTokenOperator = authorizationTokenOperator;
    }

    @GetMapping
    public List<Desk> getAllDesks(@RequestHeader("Authorization") String authorizationToken) {
        authorizationTokenOperator.getToken(authorizationToken);
        return deskService.getAllDesksToList();
    }

    @PostMapping
    public Desk addNewDesk(@RequestHeader("Authorization") String authorizationToken,
                           Desk deskToAdd) {
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
