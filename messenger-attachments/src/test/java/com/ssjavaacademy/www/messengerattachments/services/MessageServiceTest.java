package com.ssjavaacademy.www.messengerattachments.services;

import com.ssjavaacademy.www.messengerattachments.dtos.FileGetSlimDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessageGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.UserDto;
import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.MessageNotFoundException;
import com.ssjavaacademy.www.messengerattachments.mappers.MessageMapper;
import com.ssjavaacademy.www.messengerattachments.repositories.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    @Mock
    MessageRepository messageRepository;

    @Mock
    MessageMapper messageMapper;

    @InjectMocks
    MessageService messageService;

    @Mock
    UserService userService;

    Message m1;

    @BeforeEach
    void createMockObject() {
        m1 = new Message();
        m1.setCreatedAt(Instant.now());
        m1.setText("text1");
        m1.setSubject("subject1");
        m1.setMessageId(1l);
        m1.setFromUser("tosho");
        m1.setToUsers("tosho");
        m1.setRead(false);
        m1.setFiles(List.of(new File(1L, m1, "name", "https://ibb.co/qxdfyT7")));
    }

    @Test
    @DisplayName("Positive test - getting all messages in the system")
    void findAll() {
        List<Message> list = new ArrayList<>();
        list.add(m1);

        when(messageRepository.findAll()).thenReturn(list);
        List<Message> messages = messageService.findAll();
        assertEquals(1, messages.size());
        assertNotNull(messages);
    }

    @Test
    @DisplayName("Positive test - get message by id")
    void findById() {
        when(messageRepository.findById(1L)).thenReturn(Optional.of(m1));
        Message message = messageService.findById(1L).get();
        assertNotNull(message);
        assertEquals("text1", message.getText());
        assertEquals(1L, message.getMessageId());
        assertEquals("subject1", message.getSubject());
    }

    @Test
    @DisplayName("Negative test - get message by invalid id")
    void findByInvalidId() {
        try {
            messageService.findById(100L).orElseThrow(() -> new MessageNotFoundException("Message Not Found"));
        } catch (MessageNotFoundException e) {
            assertEquals("Message Not Found", e.getMessage());
        }
    }

    @Test
    @DisplayName("Positive test - delete message with valid id")
    void delete() {
        doNothing().when(messageRepository).delete(m1);
        messageService.delete(m1);
        verify(messageRepository, times(1)).delete(m1);
    }

    @Test
    @DisplayName("Positive test - update message with valid id")
    void updateMessage() throws MessageNotFoundException {
        Message m2 = new Message();
        m2.setCreatedAt(Instant.now());
        m2.setText("text1");
        m2.setSubject("edited");
        m2.setMessageId(1L);
        m2.setFromUser("tosho");
        m2.setToUsers("tosho");
        m2.setRead(false);
        m2.setFiles(List.of(new File(1L, m1, "file1", "https://ibb.co/qxdfyT7")));

        when(messageRepository.findById(1L)).thenReturn(Optional.of(m1));
        m1 = messageService.updateMessage(m1.getMessageId(), m2);
        verify(messageRepository, times(1)).save(m1);
        assertEquals("edited", m1.getSubject());
    }

    @Test
    @DisplayName("Negative test - delete message with not valid id")
    void updateInvalidIdMessage() {
        Message m2 = new Message();
        m2.setCreatedAt(Instant.now());
        m2.setText("text1");
        m2.setSubject("edited");
        m2.setMessageId(1000L);
        m2.setFromUser("tosho");
        m2.setToUsers("tosho");
        m2.setRead(false);
        m2.setFiles(List.of(new File(1L, m2, "file1", "https://ibb.co/qxdfyT7")));

        try {
            m1 = messageService.updateMessage(m1.getMessageId(), m2);
        } catch (MessageNotFoundException e) {
            assertEquals("Message not found", e.getMessage());
        }
    }

    @Test
    @DisplayName("Positive test - get all inbox messages")
    void getInboxMessages() {
        String authorization = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoidG9zaG8iLCJleHAiOjE2Njg3MTg2NzYsImlhdCI6MTY2NzgxODY3Niwic2NvcGUiOiJST0xFX1VTRVIifQ.VnLUW0jhaEygpOcOplS7yWwTo1n4ywRSAKHkDt5YTSVSvgqBkJNSAhGB3SWyUf4-zm5TsM8Jmg5-QRB5JLOBBj_Bsu4Qd0cJXHRl20Nrr1hxNB3-NTCU05BxOTDcyOzbZ9oAzegleH6utCLDePKPPdHJJ_TT9ibT9qN4o_ZViQhRvo1KRxbiWjVK_jQYvGSJnX7CETpDhtsnvClbmfVm1kuIOv_gJKo38sLI8EDz5JrF7UAe6FjuZ83ok6hrdQlPeVzftdqN-IT1fxEB4pzIiseEbQcrIzfswFjZDx26MnfglmnKn8T5EeDVTsbVqTkiAudTKemamnUKeiAeIsCDuA";

        Message m2 = new Message();
        m2.setCreatedAt(Instant.now());
        m2.setText("text");
        m2.setSubject("subject2");
        m2.setMessageId(2L);
        m2.setFromUser("tosho");
        m2.setToUsers("pesho");
        m2.setRead(false);
        m2.setFiles(List.of(new File(2L, m2, "name", "https://ibb.co/qxdfyT7")));

        MessageGetDto messageGetDto = new MessageGetDto();
        messageGetDto.setCreatedAt(Instant.now());
        messageGetDto.setText("text");
        messageGetDto.setSubject("subject2");
        messageGetDto.setMessageId(2L);
        messageGetDto.setFromUser("tosho");
        messageGetDto.setToUsers("pesho");
        messageGetDto.setRead(false);
        FileGetSlimDto fileGetSlimDto = new FileGetSlimDto(1L, "file1");
        messageGetDto.setFiles(List.of(fileGetSlimDto));

        List<Message> list = new ArrayList<>();
        list.add(m1);
        when(userService.getUser(authorization)).thenReturn(new UserDto(1, "tosho", "email"));
        when(messageRepository.findByToUsers("tosho")).thenReturn(list);

        List<MessageGetDto> messageGetDtos = new ArrayList<>();
        messageGetDtos.add(messageGetDto);
        when(messageMapper.messagesToMessageGetDtoList(list)).thenReturn(messageGetDtos);

        List<MessageGetDto> messages = messageService.getInboxMessages(authorization);
        assertEquals(1, messages.size());
        assertNotNull(messages);
    }

    @Test
    @DisplayName("Positive test - get all unread messages")
    void findByIsRead() {
        List<Message> unread = new ArrayList<>();
        unread.add(m1);
        when(messageRepository.findByIsRead(false)).thenReturn(unread);
        List<Message> messages = messageService.findByIsRead(false);
        assertEquals(1, messages.size());
        assertNotNull(messages);
        assertThat(messages.get(0).getText()).isEqualTo("text1");
    }

    @Test
    @DisplayName("Positive test - get all messages sent by user")
    void findByFromUser() {
        List<Message> sentByUser = new ArrayList<>();
        sentByUser.add(m1);
        when(messageRepository.findByFromUser("tosho")).thenReturn(sentByUser);
        List<Message> messages = messageService.findByFromUser("tosho");
        assertEquals(1, messages.size());
        assertNotNull(messages);
        assertThat(messages.get(0).getText()).isEqualTo("text1");
    }
}