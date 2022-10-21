package com.ssjavaacademy.www.messengerattachments.services;

import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message updateMessage(long id, Message message) {
        Message oldMess = messageRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Message not found"));

        return setMessage(oldMess, message);
    }

    private Message setMessage(Message oldMess, Message message) {
        oldMess.setFiles(message.getFiles());
        oldMess.setRead(message.getRead());
        oldMess.setSubject(message.getSubject());
        oldMess.setText(message.getText());

        messageRepository.save(oldMess);

        return oldMess;
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
}
