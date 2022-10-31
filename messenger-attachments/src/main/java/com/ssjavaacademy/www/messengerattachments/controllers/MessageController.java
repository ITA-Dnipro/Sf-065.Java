package com.ssjavaacademy.www.messengerattachments.controllers;

import com.ssjavaacademy.www.messengerattachments.dtos.MessageGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessagePostDto;
import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.MessageNotFoundException;
import com.ssjavaacademy.www.messengerattachments.mappers.MessageMapper;
import com.ssjavaacademy.www.messengerattachments.repositories.MessageRepository;
import com.ssjavaacademy.www.messengerattachments.services.FileService;
import com.ssjavaacademy.www.messengerattachments.services.MessageService;
import com.ssjavaacademy.www.messengerattachments.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException.isTokenEmpty;
import static com.ssjavaacademy.www.messengerattachments.mappers.MessageMapper.messageToMessageGetDto;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {
    MessageRepository messageRepository;
    MessageService messageService;
    FileService fileService;
    MessageMapper messageMapper;
    UserService userService;

    @Autowired
    public MessageController(MessageRepository messageRepository, MessageService messageService, FileService fileService, MessageMapper messageMapper,
                             UserService userService) {
        this.messageRepository = messageRepository;
        this.messageService = messageService;
        this.fileService = fileService;
        this.messageMapper = messageMapper;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<MessageGetDto>> getAllMessages(
            @RequestHeader String authorization) throws EmptyTokenException {
        HttpStatus httpStatus = HttpStatus.OK;
        List<MessageGetDto> messages = messageMapper.messagesToMessageGetDtoList(messageService.findAll());
        isTokenEmpty(authorization);

        return new ResponseEntity<>(messages, httpStatus);
    }

    @GetMapping("/unseen")
    public ResponseEntity<List<MessageGetDto>> getAllUnseenSentMessages(
            @RequestHeader String authorization) throws EmptyTokenException {
        isTokenEmpty(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        List<MessageGetDto> messages = messageMapper.messagesToMessageGetDtoList(messageRepository.findByIsRead(false));

        return new ResponseEntity<>(messages, httpStatus);
    }

    @GetMapping("/sent")
    public ResponseEntity<List<MessageGetDto>> getAllSentByLoggedUserMessages(
            @RequestHeader String authorization) throws EmptyTokenException {
        isTokenEmpty(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        List<MessageGetDto> messages = messageMapper.messagesToMessageGetDtoList(messageRepository.findByFromUser(userService.getUser(authorization).getUsername()));

        return new ResponseEntity<>(messages, httpStatus);
    }

    @GetMapping("/inbox/all")
    public ResponseEntity<List<MessageGetDto>> getInbox(
            @RequestHeader String authorization) throws EmptyTokenException {
        isTokenEmpty(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        List<MessageGetDto> body = messageService.getInboxMessages(authorization);

        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping("/inbox/new")
    public ResponseEntity<List<MessageGetDto>> getInboxNew(
            @RequestHeader String authorization) throws EmptyTokenException {
        HttpStatus httpStatus = HttpStatus.OK;
        isTokenEmpty(authorization);
        List<MessageGetDto> body = messageService.getInboxNewMessages(authorization);

        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping("/inbox/{id}")
    public ResponseEntity<MessageGetDto> getInboxMessage(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        isTokenEmpty(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        MessageGetDto body = messageService.openInboxMessage(id, authorization);

        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<Set<File>> getAllFilesByMessage(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable(value = "id") long id) throws EmptyTokenException, MessageNotFoundException {
        isTokenEmpty(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        Message message = messageService.findById(id).orElseThrow(() -> new MessageNotFoundException("Message Not Found"));
        Set<File> files = message.getFiles();

        return new ResponseEntity<>(files, httpStatus);
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestHeader(name = "Authorization") String authorization,
            @Valid @RequestPart MessagePostDto messageDto,
            @RequestParam HashSet<MultipartFile> attachments) throws EmptyTokenException, IOException {
        isTokenEmpty(authorization);
        HttpStatus httpStatus = HttpStatus.CREATED;
        messageService.createMessage(messageDto, attachments, authorization);

        return new ResponseEntity<>(httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageGetDto> getById(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        isTokenEmpty(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        MessageGetDto body = messageToMessageGetDto(messageService.findById(id).orElseThrow(() -> new MessageNotFoundException("Message Not Found")));

        return new ResponseEntity<>(body, httpStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageGetDto> update(
            @RequestHeader String authorization,
            @Valid @RequestPart MessagePostDto messageDto,
            @PathVariable(value = "id") int id) throws EmptyTokenException, MessageNotFoundException, IOException {
        HttpStatus httpStatus = HttpStatus.OK;
        isTokenEmpty(authorization);
        MessageGetDto body = messageService.updateMessageByDto(id, messageDto, new HashSet<>());

        return ResponseEntity.status(httpStatus).body(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        isTokenEmpty(authorization);
        HttpStatus status = HttpStatus.GONE;
        messageService.deleteMessage(id);

        return new ResponseEntity<>(status);
    }
}
