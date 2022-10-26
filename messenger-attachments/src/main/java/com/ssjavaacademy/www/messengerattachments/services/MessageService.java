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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.ssjavaacademy.www.messengerattachments.mappers.MessageMapper.messageToMessageGetDto;

@Service
public class MessageService {
    MessageRepository messageRepository;
    FileService fileService;
    MessageMapper messageMapper;

    @Autowired
    public MessageService(MessageRepository messageRepository, FileService fileService, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.fileService = fileService;
        this.messageMapper = messageMapper;
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
        Message message = messageMapper.messagePostDtoToMessage(messageDto, attachments);
        message = updateMessage(id, message);

        return messageToMessageGetDto(message);
    }

    @Transactional
    public Message updateMessage(long id, Message message) throws MessageNotFoundException {
        Message oldMess = messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException("Message not found"));
        //save the files first
        for (File f : message.getFiles()) {
            if (f.getFileId() == null) {
                f.setMessageId(null);
                fileService.save(f);
            }
        }
        //set the message and save it
        oldMess.setFiles(message.getFiles());
        oldMess.setRead(message.getRead());
        oldMess.setSubject(message.getSubject());
        oldMess.setText(message.getText());

        messageRepository.save(oldMess);

        //set the files message and save the file again
        for (File f : oldMess.getFiles()) {
            if (f.getMessageId() == null) {
                f.setMessageId(oldMess);
                fileService.save(f);
            }
        }

        return oldMess;
    }

    public static Set<File> setMessageFilesSet(Set<File> fileSet, Set<MultipartFile> documents, Message message) throws IOException {
        outer:
        for (MultipartFile s : documents) {
            for (File f : fileSet) {
                if (f.getFileName().equals(s.getOriginalFilename())) {
                    continue outer;
                }
            }
            File f = new File();
            f.setMessageId(message);
            f.setFileContent(s.getBytes());
            f.setFileName(s.getOriginalFilename());
            f.setFileSize(s.getSize());
            fileSet.add(f);
        }

        return fileSet;
    }
}
