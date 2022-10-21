package com.officemap.controller;

import com.officemap.authorization.Token;
import com.officemap.authorization.TokenOperator;
import com.officemap.entity.Desk;
import com.officemap.service.DeskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/desk")
public class DeskController {

    private final DeskService deskService;
    private final TokenOperator tokenOperator;

    public DeskController(DeskService deskService, TokenOperator tokenOperator) {
        this.deskService = deskService;
        this.tokenOperator = tokenOperator;
    }

    @GetMapping
    public List<Desk> getAllDesks(@RequestHeader("Authorization") String authorizationToken) {
        tokenOperator.getToken(authorizationToken);
        return deskService.getAllDesksToList();
    }

    @PostMapping
    public Desk addNewDesk(@RequestHeader("Authorization") String authorizationToken,
                           Desk deskToAdd) {
        tokenOperator.getToken(authorizationToken);
        return deskService.createNewDesk(deskToAdd);
    }

    @DeleteMapping("/{id}")
    public boolean deleteDeskById(@RequestHeader("Authorization") String authorizationToken,
                                  @PathVariable("id") Long deskId) {
        tokenOperator.getToken(authorizationToken);
        return deskService.deleteDeskById(deskId);
    }

    @GetMapping("/tokens")
    public List<Token> getAllTokens() {
        return tokenOperator.getTokensList();
    }
}
