package com.ssjavaacademy.www.messengerattachments.controllers;

import com.ssjavaacademy.www.messengerattachments.dtos.MessageGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessagePostDto;
import com.ssjavaacademy.www.messengerattachments.dtos.UserDto;
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
import org.springframework.http.MediaType;
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
import static com.ssjavaacademy.www.messengerattachments.services.MessageService.setMessageFilesSet;

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
    public ResponseEntity<List<Message>> getAllMessages(
            @RequestHeader String authorization) throws EmptyTokenException {
        HttpStatus httpStatus = HttpStatus.OK;
        List<Message> messages = messageService.findAll();
        isTokenEmpty(authorization);

        return new ResponseEntity<>(messages, httpStatus);
    }

    @GetMapping("/unseen")
    public ResponseEntity<List<Message>> getAllUnseenSentMessages(
            @RequestHeader String authorization) throws EmptyTokenException {
        HttpStatus httpStatus = HttpStatus.OK;
        List<Message> messages = messageRepository.findByIsRead(false);
        isTokenEmpty(authorization);

        return new ResponseEntity<>(messages, httpStatus);
    }

    @GetMapping("/sent")
    public ResponseEntity<List<Message>> getAllSentByLoggedUserMessages(
            @RequestHeader String authorization) throws EmptyTokenException {
        HttpStatus httpStatus = HttpStatus.OK;
        List<Message> messages = messageRepository.findByFromUser(userService.getUser(authorization).getUsername());
        isTokenEmpty(authorization);

        return new ResponseEntity<>(messages, httpStatus);
    }

    @GetMapping("/inbox")
    public ResponseEntity<List<Message>> getInbox(
            @RequestHeader String authorization) throws EmptyTokenException {
        HttpStatus httpStatus = HttpStatus.OK;
        List<Message> messages = messageRepository.findByToUsers(userService.getUser(authorization).getUsername());
        isTokenEmpty(authorization);

        return new ResponseEntity<>(messages, httpStatus);
    }


    @GetMapping("/{id}/files")
    public ResponseEntity<Set<File>> getAllFilesByMessage(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable(value = "id") long id) throws EmptyTokenException, MessageNotFoundException {
        HttpStatus httpStatus = HttpStatus.OK;
        Message message = messageService.findById(id).orElseThrow(() -> new MessageNotFoundException("Message Not Found"));
        Set<File> files = message.getFiles();
        isTokenEmpty(authorization);

        return new ResponseEntity<>(files, httpStatus);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(
            @RequestHeader(name = "Authorization") String authorization,
            @Valid @RequestPart MessagePostDto messageDto,
            @RequestParam HashSet<MultipartFile> attachments) throws EmptyTokenException, IOException {
        HttpStatus httpStatus = HttpStatus.CREATED;
        isTokenEmpty(authorization);
        Message message = messageMapper.messagePostDtoToMessage(messageDto, attachments);
        setMessageFilesSet(attachments, message);
        message.setFromUser(userService.getUser(authorization).getUsername());
        messageRepository.save(message);

        Set<File> files = message.getFiles();
        if (files != null) {
            fileService.uploadFiles(files);
        }

        return new ResponseEntity<>(httpStatus);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MessageGetDto> getById(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        HttpStatus httpStatus = HttpStatus.OK;

        isTokenEmpty(authorization);
        Message message = messageService.findById(id).orElseThrow(() -> new MessageNotFoundException("Message Not Found"));
        MessageGetDto body = messageToMessageGetDto(message);

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
    public ResponseEntity<Message> delete(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        HttpStatus status = HttpStatus.GONE;
        isTokenEmpty(authorization);
        Message body = messageService.findById(id).orElseThrow(() -> new MessageNotFoundException("Message Not Found"));
        messageService.delete(body);

        return new ResponseEntity<>(status);
    }
}
