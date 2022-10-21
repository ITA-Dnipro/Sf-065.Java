package com.ssjavaacademy.www.messengerattachments.controllers;

import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.repositories.MessageRepository;
import com.ssjavaacademy.www.messengerattachments.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageService messageService;

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages(@RequestHeader String authorization) {
        HttpStatus httpStatus = HttpStatus.OK;

        if (authorization == null || authorization.equals("")) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        List<Message> messages = messageRepository.findAll();

        return new ResponseEntity<>(messages, httpStatus);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestHeader String authorization,
            @Valid @RequestBody Message message) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        messageRepository.save(message);

        return new ResponseEntity<>(httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getById(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) {
        HttpStatus httpStatus = HttpStatus.OK;
        if (authorization == null || authorization.equals("")) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(new Message(), httpStatus);
        }
        Message body = messageRepository.findById(id).orElseThrow(()->new NullPointerException("Message Not Found"));

        return new ResponseEntity<>(body, httpStatus);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Message> update(
            @Valid @RequestBody Message message,
            @RequestHeader String authorization,
            @PathVariable(value = "id") int id) {
        HttpStatus httpStatus = HttpStatus.OK;

        if (Objects.equals(authorization, null)) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        Message body = messageService.updateMessage(id, message);

        return ResponseEntity.status(httpStatus).body(body);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable(value = "id") long id, @RequestHeader String authorization) {
        HttpStatus status = HttpStatus.GONE;

        if (Objects.equals(authorization, null) || Objects.equals(authorization, "")) {
             status = HttpStatus.UNAUTHORIZED;
        }

        Message body = messageRepository.findById(id).orElseThrow(()-> new NullPointerException("Message Not Found"));
        messageRepository.delete(body);

        return new ResponseEntity<>(status);
    }
}
