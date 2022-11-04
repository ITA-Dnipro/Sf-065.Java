package com.ssjavaacademy.www.messengerattachments.services;

import com.ssjavaacademy.www.messengerattachments.dtos.MessageGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessagePostDto;
import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.MessageNotFoundException;
import com.ssjavaacademy.www.messengerattachments.mappers.MessageMapper;
import com.ssjavaacademy.www.messengerattachments.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public MessageGetDto updateMessageByDto(int id, MessagePostDto messageDto, HashSet<MultipartFile> attachments) throws IOException, MessageNotFoundException {
        Message message = messageMapper.messagePostDtoToMessage(messageDto);
        setMessageFilesSet(attachments, message);
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

    public static void setMessageFilesSet(Set<MultipartFile> documents, Message message) throws IOException {
        Set<File> fileSet = message.getFiles();

        for (MultipartFile s : documents) {
                File f = new File();
                f.setMessageId(message);
                f.setFileContent(s.getBytes());
                f.setFileName(s.getOriginalFilename());
                f.setFileSize(s.getSize());
                fileSet.add(f);
        }
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

    public Message getInboxMessageById(long id,List<Message> inboxMessages) throws MessageNotFoundException {
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

    public void createMessage(MessagePostDto messageDto, HashSet<MultipartFile> attachments, String authorization) throws IOException {
        Message message = messageMapper.messagePostDtoToMessage(messageDto);
        setMessageFilesSet(attachments, message);
        message.setFromUser(userService.getUser(authorization).getUsername());
        messageRepository.save(message);

        Set<File> files = message.getFiles();
        if (files != null) {
            fileService.uploadFiles(files);
        }
    }

    public void deleteMessage(long id) throws MessageNotFoundException {
        Message body = findById(id).orElseThrow(() -> new MessageNotFoundException("Message Not Found"));
        delete(body);
    }
}
