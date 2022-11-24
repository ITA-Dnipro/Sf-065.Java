package com.ssjavaacademy.www.messengerattachments.controllers;

import com.ssjavaacademy.www.messengerattachments.dtos.FileGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessageGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessagePostDto;
import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.EmptyTokenException;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.MessageNotFoundException;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.NotValidJsonBodyException;
import com.ssjavaacademy.www.messengerattachments.mappers.FileMapper;
import com.ssjavaacademy.www.messengerattachments.mappers.MessageMapper;
import com.ssjavaacademy.www.messengerattachments.services.FileService;
import com.ssjavaacademy.www.messengerattachments.services.MessageService;
import com.ssjavaacademy.www.messengerattachments.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ssjavaacademy.www.messengerattachments.mappers.MessageMapper.messageToMessageGetDto;
import static com.ssjavaacademy.www.messengerattachments.validators.JsonRequestValidator.validateJsonInput;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@SecurityRequirement(name = "Authorization")
@RequestMapping("api/v1/messages")
public class MessageController {
    MessageService messageService;
    FileService fileService;
    MessageMapper messageMapper;
    UserService userService;
    FileMapper fileMapper;

    @Autowired
    public MessageController(MessageService messageService, FileService fileService, MessageMapper messageMapper,
                             UserService userService, FileMapper fileMapper) {
        this.messageService = messageService;
        this.fileService = fileService;
        this.messageMapper = messageMapper;
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<MessageGetDto>> getAllMessages(
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        HttpStatus httpStatus = HttpStatus.OK;
        List<MessageGetDto> messages = messageMapper.messagesToMessageGetDtoList(messageService.findAll());
        for (MessageGetDto f : messages) {
            Link singular = linkTo(methodOn(MessageController.class).getById(f.getMessageId(), authorization)).withRel("open message");
            f.add(singular);
        }
        userService.validateToken(authorization);

        Link link = linkTo(methodOn(MessageController.class).getAllMessages(authorization)).withSelfRel();
        Link unseen = linkTo(methodOn(MessageController.class).getAllUnseenSentMessages(authorization)).withRel("all unseen messages");

        return new ResponseEntity<>(CollectionModel.of(messages, link, unseen), httpStatus);
    }

    @GetMapping("/unseen")
    public ResponseEntity<CollectionModel<MessageGetDto>> getAllUnseenSentMessages(
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        List<MessageGetDto> messages = messageMapper.messagesToMessageGetDtoList(messageService.findByIsRead(false));
        for (MessageGetDto f : messages) {
            Link singular = linkTo(methodOn(MessageController.class).getById(f.getMessageId(), authorization)).withSelfRel();
            f.add(singular);
        }
        Link allMessLink = linkTo(methodOn(MessageController.class).getAllMessages(authorization)).withRel("all messages");

        Link link = linkTo(methodOn(MessageController.class).getAllUnseenSentMessages(authorization)).withSelfRel();
        return new ResponseEntity<>(CollectionModel.of(messages, link, allMessLink), httpStatus);
    }

    @GetMapping("/sent")
    public ResponseEntity<CollectionModel<MessageGetDto>> getAllSentByLoggedUserMessages(
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        List<MessageGetDto> messages = messageMapper.messagesToMessageGetDtoList(messageService.findByFromUser(userService.getUser(authorization).getUsername()));
        for (MessageGetDto f : messages) {
            Link singular = linkTo(methodOn(MessageController.class).getById(f.getMessageId(), authorization)).withSelfRel();
            f.add(singular);
        }
        Link sentByCurrUser = linkTo(methodOn(MessageController.class).getAllSentByLoggedUserMessages(authorization)).withRel("sentByUser");
        Link sentToCurrUser = linkTo(methodOn(MessageController.class).getInbox(authorization)).withRel("sentToUser");
        Link inboxNew = linkTo(methodOn(MessageController.class).getInboxNew(authorization)).withRel("inboxNew");

        return new ResponseEntity<>(CollectionModel.of(messages, sentByCurrUser, sentToCurrUser, inboxNew), httpStatus);
    }

    @GetMapping("/inbox/all")
    public ResponseEntity<CollectionModel<MessageGetDto>> getInbox(
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        List<MessageGetDto> body = messageService.getInboxMessages(authorization);
        for (MessageGetDto f : body) {
            Link singular = linkTo(methodOn(MessageController.class).getById(f.getMessageId(), authorization)).withSelfRel();
            f.add(singular);
        }
        Link sentToCurrUser = linkTo(methodOn(MessageController.class).getInbox(authorization)).withSelfRel();
        Link sentByCurrUser = linkTo(methodOn(MessageController.class).getAllSentByLoggedUserMessages(authorization)).withRel("sentByUser");
        Link inboxNew = linkTo(methodOn(MessageController.class).getInboxNew(authorization)).withRel("inboxNew");

        return new ResponseEntity<>(CollectionModel.of(body, sentByCurrUser, sentToCurrUser, inboxNew), httpStatus);
    }

    @GetMapping("/inbox/new")
    public ResponseEntity<CollectionModel<MessageGetDto>> getInboxNew(
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException {
        HttpStatus httpStatus = HttpStatus.OK;
        userService.validateToken(authorization);
        List<MessageGetDto> body = messageService.getInboxNewMessages(authorization);
        for (MessageGetDto f : body) {
            Link singular = linkTo(methodOn(MessageController.class).getById(f.getMessageId(), authorization)).withSelfRel();
            f.add(singular);
        }
        Link inboxNew = linkTo(methodOn(MessageController.class).getInboxNew(authorization)).withSelfRel();
        Link inbox = linkTo(methodOn(MessageController.class).getInbox(authorization)).withRel("inbox");
        Link sentByCurrUser = linkTo(methodOn(MessageController.class).getAllSentByLoggedUserMessages(authorization)).withRel("sentByUser");

        return new ResponseEntity<>(CollectionModel.of(body, sentByCurrUser, inbox, inboxNew), httpStatus);
    }

    @GetMapping("/inbox/{id}")
    public ResponseEntity<MessageGetDto> getInboxMessage(
            @PathVariable(value = "id") long id,
            @RequestHeader String authorization) throws EmptyTokenException, MessageNotFoundException, InvocationTargetException, IllegalAccessException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        MessageGetDto body = messageService.openInboxMessage(id, authorization);

        Link self = linkTo(methodOn(MessageController.class).getById(body.getMessageId(), authorization)).withSelfRel();
        Link linkGetById = linkTo(methodOn(MessageController.class).getById(body.getMessageId(), authorization)).withRel("getById");
        Link inboxNew = linkTo(methodOn(MessageController.class).getInboxNew(authorization)).withRel("inboxNew");
        Link inbox = linkTo(methodOn(MessageController.class).getInbox(authorization)).withRel("inbox");
        Link sentByCurrUser = linkTo(methodOn(MessageController.class).getAllSentByLoggedUserMessages(authorization)).withRel("sentByUser");
        Set<Link> links = Set.of(inboxNew, inbox, sentByCurrUser, linkGetById, self);
        body.add(links);

        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<CollectionModel<FileGetDto>> getAllFilesByMessage(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable(value = "id") long id) throws EmptyTokenException, MessageNotFoundException {
        userService.validateToken(authorization);
        HttpStatus httpStatus = HttpStatus.OK;
        Message message = messageService.findById(id).orElseThrow(() -> new MessageNotFoundException("Message Not Found"));
        List<FileGetDto> files = fileMapper.fileSetToFileGetDtoSet(message.getFiles());

        for (FileGetDto f : files) {
            Link singular = linkTo(methodOn(MessageController.class).getById(f.getFileId(), authorization)).withRel("openFile");
            f.add(singular);
        }
        Link link = linkTo(methodOn(MessageController.class).getAllFilesByMessage(authorization, id)).withSelfRel();
        Link linkGetById = linkTo(methodOn(MessageController.class).getById(id, authorization)).withRel("getById");

        return new ResponseEntity<>(CollectionModel.of(files, link, linkGetById), httpStatus);
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestHeader(name = "Authorization") String authorization,
            @Valid @RequestBody MessagePostDto messageDto) throws EmptyTokenException, IOException, NotValidJsonBodyException {
        userService.validateToken(authorization);
        validateJsonInput(messageDto);
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
        Link self = linkTo(methodOn(MessageController.class).getById(body.getMessageId(), authorization)).withSelfRel();
        body.add(self);
        Link link = linkTo(methodOn(MessageController.class).getAllMessages(authorization)).withRel("allMessages");
        body.add(link);

        return new ResponseEntity<>(body, httpStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageGetDto> update(
            @RequestHeader String authorization,
            @Valid @RequestBody MessagePostDto messageDto,
            @PathVariable(value = "id") int id) throws EmptyTokenException, MessageNotFoundException, IOException, NotValidJsonBodyException {
        HttpStatus httpStatus = HttpStatus.OK;
        validateJsonInput(messageDto);
        userService.validateToken(authorization);
        MessageGetDto body = messageService.updateMessageByDto(id, messageDto, new HashSet<>());
        Link self = linkTo(methodOn(MessageController.class).getById(body.getMessageId(), authorization)).withSelfRel();
        body.add(self);

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
