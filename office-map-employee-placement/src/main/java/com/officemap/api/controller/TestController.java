package com.officemap.api.controller;

import com.officemap.authorization.AuthorizationToken;
import com.officemap.authorization.AuthorizationTokenOperator;
import com.officemap.entities.Test;
import com.officemap.repositories.TestRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/test")
public class TestController {

    private final TestRepository testRepository;
    private final AuthorizationTokenOperator authorizationTokenOperator;

    public TestController(TestRepository testRepository, AuthorizationTokenOperator authorizationTokenOperator) {
        this.testRepository = testRepository;
        this.authorizationTokenOperator = authorizationTokenOperator;
    }

    @GetMapping
    public List<Test> getTests(@RequestHeader("Authorization") String authorizationToken) {
        authorizationTokenOperator.getTokenValue(authorizationToken);
        return testRepository.findAll().stream().toList();
    }

    @GetMapping("/basic")
    public List<Test> getTestsNoTokens() {
        return testRepository.findAll().stream().toList();
    }

    @GetMapping("/tokens")
    public List<AuthorizationToken> getAllTokens(@RequestHeader("Connection") String authorizationToken) {
        authorizationTokenOperator.getTokenValue(authorizationToken);
        return authorizationTokenOperator.getAllTokensToList();
    }

    @PostMapping
    public Test addTest(@RequestHeader("Authorization") String authorizationToken,
                        @RequestBody Test test) {
        authorizationTokenOperator.getTokenValue(authorizationToken);
        testRepository.save(test);
        return test;
    }

    @DeleteMapping("/{id}")
    public boolean deleteTest(@RequestHeader("Authorization") String authorizationToken,
                              @PathVariable("id") Long id) {
        authorizationTokenOperator.getTokenValue(authorizationToken);
        testRepository.deleteById(id);
        return true;
    }
}
