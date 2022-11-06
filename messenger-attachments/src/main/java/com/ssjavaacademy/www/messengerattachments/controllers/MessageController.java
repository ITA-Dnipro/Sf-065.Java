package com.ssjavaacademy.www.messengerattachments.controllers;

import com.ssjavaacademy.www.messengerattachments.dtos.FileGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessageGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessagePostDto;
import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.MessageNotFoundException;
import com.ssjavaacademy.www.messengerattachments.mappers.FileMapper;
import com.ssjavaacademy.www.messengerattachments.mappers.MessageMapper;
import com.ssjavaacademy.www.messengerattachments.services.FileService;
import com.ssjavaacademy.www.messengerattachments.services.MessageService;
import com.ssjavaacademy.www.messengerattachments.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ssjavaacademy.www.messengerattachments.mappers.MessageMapper.messageToMessageGetDto;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {
    MessageService messageService;
    FileService fileService;
    MessageMapper messageMapper;
    UserService userService;
    FileMapper fileMapper;

    @Autowired
    public MessageController( MessageService messageService, FileService fileService, MessageMapper messageMapper,
                             UserService userService, FileMapper fileMapper) {
        this.messageService = messageService;
        this.fileService = fileService;
        this.messageMapper = messageMapper;
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    @GetMapping
    public ResponseEntity<List<MessageGetDto>> getAllMessages(
            @RequestHeader String authorization) throws EmptyTokenException {
        HttpStatus httpStatus = HttpStatus.OK;
        List<MessageGetDto> messages = messageMapper.messagesToMessageGetDtoList(messageService.findAll());
        userService.validateToken(authorization);

        return new ResponseEntity<>(messages, httpStatus);
    }

    @GetMapping("/unseen")
    public ResponseEntity<List<MessageGetDto>> getAllUnseenSentMessages(
            @RequestHeader String authorization) throws EmptyTokenException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        List<MessageGetDto> messages = messageMapper.messagesToMessageGetDtoList(messageService.findByIsRead(false));

        return new ResponseEntity<>(messages, httpStatus);
    }

    @GetMapping("/sent")
    public ResponseEntity<List<MessageGetDto>> getAllSentByLoggedUserMessages(
            @RequestHeader String authorization) throws EmptyTokenException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        List<MessageGetDto> messages = messageMapper.messagesToMessageGetDtoList(messageService.findByFromUser(userService.getUser(authorization).getUsername()));

        return new ResponseEntity<>(messages, httpStatus);
    }

    @GetMapping("/inbox/all")
    public ResponseEntity<List<MessageGetDto>> getInbox(
            @RequestHeader String authorization) throws EmptyTokenException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        List<MessageGetDto> body = messageService.getInboxMessages(authorization);

        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping("/inbox/new")
    public ResponseEntity<List<MessageGetDto>> getInboxNew(
            @RequestHeader String authorization) throws EmptyTokenException {
        HttpStatus httpStatus = HttpStatus.OK;
        userService.validateToken(authorization);
        List<MessageGetDto> body = messageService.getInboxNewMessages(authorization);

        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping("/inbox/{id}")
    public ResponseEntity<MessageGetDto> getInboxMessage(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        MessageGetDto body = messageService.openInboxMessage(id, authorization);

        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<Set<FileGetDto>> getAllFilesByMessage(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable(value = "id") long id) throws EmptyTokenException, MessageNotFoundException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        Message message = messageService.findById(id).orElseThrow(() -> new MessageNotFoundException("Message Not Found"));
        Set<FileGetDto> files = fileMapper.fileSetToFileGetDtoSet(message.getFiles());

        return new ResponseEntity<>(files, httpStatus);
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestHeader(name = "Authorization") String authorization,
            @Valid @RequestBody MessagePostDto messageDto) throws EmptyTokenException, IOException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.CREATED;
        messageService.createMessage(messageDto, authorization);

        return new ResponseEntity<>(httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageGetDto> getById(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        MessageGetDto body = messageToMessageGetDto(messageService.findById(id).orElseThrow(() -> new MessageNotFoundException("Message Not Found")));

        return new ResponseEntity<>(body, httpStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageGetDto> update(
            @RequestHeader String authorization,
            @Valid @RequestBody MessagePostDto messageDto,
            @PathVariable(value = "id") int id) throws EmptyTokenException, MessageNotFoundException, IOException {
        HttpStatus httpStatus = HttpStatus.OK;
        userService.validateToken(authorization);
        MessageGetDto body = messageService.updateMessageByDto(id, messageDto, new HashSet<>());

        return ResponseEntity.status(httpStatus).body(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        userService.validateToken(authorization);
        HttpStatus status = HttpStatus.GONE;
        messageService.deleteMessage(id);

        return new ResponseEntity<>(status);
    }
}
