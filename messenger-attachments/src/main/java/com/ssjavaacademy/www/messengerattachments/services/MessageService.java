package com.ssjavaacademy.www.messengerattachments.services;

import com.ssjavaacademy.www.messengerattachments.dtos.MessageGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessagePostDto;
import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.MessageNotFoundException;
import com.ssjavaacademy.www.messengerattachments.mappers.MessageMapper;
import com.ssjavaacademy.www.messengerattachments.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.*;

import static com.ssjavaacademy.www.messengerattachments.mappers.MessageMapper.messageToMessageGetDto;

@Service
public class MessageService {
    MessageRepository messageRepository;
    FileService fileService;
    MessageMapper messageMapper;
    UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, FileService fileService, MessageMapper messageMapper, UserService userService) {
        this.messageRepository = messageRepository;
        this.fileService = fileService;
        this.messageMapper = messageMapper;
        this.userService = userService;
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Optional<Message> findById(long id) {
        return messageRepository.findById(id);
    }

    public void delete(Message body) {
        messageRepository.delete(body);
    }

    public MessageGetDto updateMessageByDto(int id, MessagePostDto messageDto, HashSet<String> attachments) throws IOException, MessageNotFoundException {
        Message message = messageMapper.messagePostDtoToMessage(messageDto);
        setMessageFilesSet(message, attachments);
        message = updateMessage(id, message);

        return messageToMessageGetDto(message);
    }

    public Message updateMessage(long id, Message message) throws MessageNotFoundException {
        Message oldMess = messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException("Message not found"));
        oldMess.setSubject(message.getSubject());
        oldMess.setText(message.getText());
        oldMess.setToUsers(message.getToUsers());
        messageRepository.save(oldMess);

        return oldMess;
    }

    public static void setMessageFilesSet(Message message, Set<String> files) throws IOException {
        List<File> fileSet = message.getFiles();

        for (String s : files) {
            File f = new File();
            f.setMessageId(message);
            String[] agh = s.split("/");

            f.setFileName(agh[agh.length - 2] + LocalDateTime.now().getNano());
            f.setFilePath(s);
            fileSet.add(f);
        }
        message.setFiles(fileSet);
    }

    public List<MessageGetDto> getInboxNewMessages(String authorization) {
        String keyword = userService.getUser(authorization).getUsername();
        List<Message> messages = messageRepository.findByKeyword(keyword, false);
        return messageMapper.messagesToMessageGetDtoList(messages);
    }

    public List<MessageGetDto> getInboxMessages(String authorization) {
        List<Message> messages = messageRepository.findByToUsers(userService.getUser(authorization).getUsername());
        return messageMapper.messagesToMessageGetDtoList(messages);
    }

    public MessageGetDto openInboxMessage(long id, String authorization) throws MessageNotFoundException {
        List<Message> inboxMessages = messageRepository.findByToUsers(userService.getUser(authorization).getUsername());

        Message message = getInboxMessageById(id, inboxMessages);
        message.setRead(true);
        messageRepository.save(message);

        return messageToMessageGetDto(message);
    }

    public Message getInboxMessageById(long id, List<Message> inboxMessages) throws MessageNotFoundException {
        boolean isInInbox = false;
        Message message = null;
        for (Message m : inboxMessages) {
            int id1 = Math.toIntExact(m.getMessageId());
            int id2 = Math.toIntExact(id);

            if (id1 == id2) {
                isInInbox = true;
                message = m;
                break;
            }
        }
        if (!isInInbox) {
            throw new MessageNotFoundException("Message not found in user inbox");
        }

        return message;
    }

    public void createMessage(MessagePostDto messageDto, String authorization) throws IOException {
        Message message = messageMapper.messagePostDtoToMessage(messageDto);

        Set<String> filePaths = messageDto.getAttachments();
        setMessageFilesSet(message, filePaths);
        message.setFromUser(userService.getUser(authorization).getUsername());
        messageRepository.save(message);

        List<File> files = message.getFiles();
        if (files != null) {
            fileService.uploadFiles(files);
        }
    }

    public void deleteMessage(long id) throws MessageNotFoundException {
        Message body = findById(id).orElseThrow(() -> new MessageNotFoundException("Message Not Found"));
        delete(body);
    }

    public List<Message> findByIsRead(boolean b) {
        return messageRepository.findByIsRead(b);
    }

    public List<Message> findByFromUser(String username) {
        return messageRepository.findByFromUser(username);
    }
}
