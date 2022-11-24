package com.ssjavaacademy.www.messengerattachments.repositories;

import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.entities.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MessageRepositoryTest {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    FileRepository fileRepository;

@BeforeEach
void createMockObject() {
    Message m1 = new Message();
    m1.setCreatedAt(Instant.now());
    m1.setText("text1");
    m1.setSubject("subject1");
    m1.setFromUser("tosho");
    m1.setToUsers("tosho");
    m1.setRead(false);
    messageRepository.save(m1);

    Message m2 = new Message();
    m2.setCreatedAt(Instant.now());
    m2.setText("text1");
    m2.setSubject("subject1");
    m2.setFromUser("pesho");
    m2.setToUsers("tosho");
    m2.setRead(true);
    messageRepository.save(m2);

    File f1 = new File();
    f1.setFileName("name");
    f1.setMessageId(m1);
    f1.setFilePath("https://ibb.co/qxdfyT7");
    m1.setFiles(List.of(f1));
    fileRepository.save(f1);
}

@AfterEach
void deleteTestData() {
    messageRepository.deleteAll();
    fileRepository.deleteAll();
}

    @Test
    @DisplayName("Positive test - find list of messages sent by user")
    void findByFromUserPositive() {
       List<Message> actual =  messageRepository.findByFromUser("tosho");
        assertEquals(1,actual.size());
        assertEquals("text1",actual.get(0).getText());
    }

    @Test
    @DisplayName("Positive test - find list of unopened/new  messages")
    void findByIsRead() {
        List<Message> actual =  messageRepository.findByIsRead(false);
        assertEquals(1,actual.size());
    }

    @Test
    @DisplayName("Positive test - find list of messages by keyword")
    void findByToUsers() {
        List<Message> actual =  messageRepository.findByToUsers("tosho");
        assertEquals(2,actual.size());
        assertEquals("tosho",actual.get(0).getFromUser());
        assertEquals("pesho",actual.get(1).getFromUser());
    }

    @Test
    @DisplayName("Positive test - find list of messages by keyword")
    void findByKeyword() {
        List<Message> actual =  messageRepository.findByToUsers("tosho");
        assertEquals(2,actual.size());
        assertEquals("tosho",actual.get(0).getToUsers());
        assertEquals("tosho",actual.get(1).getToUsers());
    }
}