package com.ssjavaacademy.www.messengerattachments.controllers;

import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.MessageNotFoundException;
import com.ssjavaacademy.www.messengerattachments.repositories.MessageRepository;
import com.ssjavaacademy.www.messengerattachments.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException.isTokenEmpty;

@RestController
@RequestMapping("/messages")
public class MessageController {
    MessageRepository messageRepository;
    MessageService messageService;

    @Autowired
    public MessageController(MessageRepository messageRepository, MessageService messageService) {
        this.messageRepository = messageRepository;
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages(@RequestHeader String authorization) throws EmptyTokenException {
        HttpStatus httpStatus = HttpStatus.OK;

        List<Message> messages = messageService.findAll();
        isTokenEmpty(authorization);

        return new ResponseEntity<>(messages, httpStatus);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestHeader String authorization,
            @Valid @RequestBody Message message) throws EmptyTokenException {
        HttpStatus httpStatus = HttpStatus.CREATED;

        isTokenEmpty(authorization);
        messageRepository.save(message);

        return new ResponseEntity<>(httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getById(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        HttpStatus httpStatus = HttpStatus.OK;

        isTokenEmpty(authorization);
        Message body = messageService.findById(id).orElseThrow(() -> new MessageNotFoundException("Message Not Found"));

        return new ResponseEntity<>(body, httpStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> update(
            @Valid @RequestBody Message message,
            @RequestHeader String authorization,
            @PathVariable(value = "id") int id) throws EmptyTokenException {
        HttpStatus httpStatus = HttpStatus.OK;

        isTokenEmpty(authorization);
        Message body = messageService.updateMessage(id, message);

        return ResponseEntity.status(httpStatus).body(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Message> delete(@PathVariable(value = "id") long id, @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        HttpStatus status = HttpStatus.GONE;

        isTokenEmpty(authorization);
        Message body = messageService.findById(id).orElseThrow(()-> new MessageNotFoundException("Message Not Found"));
        messageService.delete(body);

        return new ResponseEntity<>(status);
    }
}
