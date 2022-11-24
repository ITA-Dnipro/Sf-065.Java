package com.ssjavaacademy.www.messengerattachments.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssjavaacademy.www.messengerattachments.dtos.FileGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessageGetDto;
import com.ssjavaacademy.www.messengerattachments.dtos.MessagePostDto;
import com.ssjavaacademy.www.messengerattachments.dtos.UserDto;
import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.exceptionHandlers.MessageNotFoundException;
import com.ssjavaacademy.www.messengerattachments.mappers.FileMapper;
import com.ssjavaacademy.www.messengerattachments.mappers.MessageMapper;
import com.ssjavaacademy.www.messengerattachments.repositories.MessageRepository;
import com.ssjavaacademy.www.messengerattachments.services.FileService;
import com.ssjavaacademy.www.messengerattachments.services.MessageService;
import com.ssjavaacademy.www.messengerattachments.services.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MessageController.class)
class MessageControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MessageService messageService;

    @MockBean
    FileService fileService;

    @MockBean
    MessageMapper messageMapper;

    @MockBean
    UserService userService;

    @MockBean
    FileMapper fileMapper;

    @MockBean
    MessageRepository messageRepository;

    @InjectMocks
    MessageController messageController;

    String authorization;
    List<MessageGetDto> expected = new ArrayList<>();
    MessageGetDto expectedDto;

    @Before
    public void setUp() {
    }

    Message message1;

    @BeforeEach
    void setUpBeforeEachTest() {
        message1 = new Message();
        message1.setMessageId(1L);
        message1.setToUsers("test1");
        message1.setRead(false);
        message1.setSubject("test1");
        message1.setText("text");
        message1.setFromUser("pesho");
        message1.setCreatedAt(Instant.now());
        message1.setFiles(new ArrayList<>());

        expectedDto = new MessageGetDto();
        expectedDto.setMessageId(1L);
        expectedDto.setToUsers("tosho");
        expectedDto.setRead(false);
        expectedDto.setSubject("test1");
        expectedDto.setText("text");
        expectedDto.setFromUser("pesho");
        expectedDto.setCreatedAt(Instant.now());
        expected = new ArrayList<>();
        expected.add(expectedDto);

       authorization = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoidG9zaG8iLCJleHAiOjE2Njg3MTg2NzYsImlhdCI6MTY2NzgxODY3Niwic2NvcGUiOiJST0xFX1VTRVIifQ.VnLUW0jhaEygpOcOplS7yWwTo1n4ywRSAKHkDt5YTSVSvgqBkJNSAhGB3SWyUf4-zm5TsM8Jmg5-QRB5JLOBBj_Bsu4Qd0cJXHRl20Nrr1hxNB3-NTCU05BxOTDcyOzbZ9oAzegleH6utCLDePKPPdHJJ_TT9ibT9qN4o_ZViQhRvo1KRxbiWjVK_jQYvGSJnX7CETpDhtsnvClbmfVm1kuIOv_gJKo38sLI8EDz5JrF7UAe6FjuZ83ok6hrdQlPeVzftdqN-IT1fxEB4pzIiseEbQcrIzfswFjZDx26MnfglmnKn8T5EeDVTsbVqTkiAudTKemamnUKeiAeIsCDuA";
    }

    @Test
    @DisplayName("Positive test - get all messages in the system with valid bearer token")
    void getAllMessages() throws Exception {
           when(messageMapper.messagesToMessageGetDtoList(messageService.findAll())).thenReturn(expected);

        this.mockMvc.perform(get("/api/v1/messages").accept(MediaType.APPLICATION_JSON).header("authorization", authorization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..fromUser").value("pesho"))
        .andExpect(content().contentType("application/json"));
    }

    @Test
    @DisplayName("Positive test - get all new/unseen messages with valid bearer token")
    void getAllUnseenSentMessages() throws Exception {
        when(messageMapper.messagesToMessageGetDtoList(messageService.findByIsRead(false))).thenReturn(expected);

        mockMvc.perform(get("/api/v1/messages/unseen").contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", authorization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..isRead").value(false))
                .andExpect(jsonPath("$..messageId").value(1));

    }

    @Test
    @DisplayName("Positive test - get all sent by authorized user messages with valid bearer token")
    void getAllSentByLoggedUserMessages() throws Exception {
        when(messageMapper.messagesToMessageGetDtoList(messageService.findByFromUser("tosho"))).thenReturn(expected);
        when(userService.getUser(authorization)).thenReturn(new UserDto(1, "tosho", "email"));
        mockMvc.perform(get("/api/v1/messages/sent").contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", authorization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..fromUser").value("pesho"));
    }

    @Test
    @DisplayName("Negative test - get all sent by authorized user messages with no bearer token")
    void getAllSentByLoggedUserMessagesNoToken() throws Exception {
        mockMvc.perform(get("/api/v1/messages/sent").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Authorization token is empty"));
    }

    @Test
    @DisplayName("Positive test - get all inbox messages for authorized user")
    void getInbox() throws Exception {
        when(messageService.getInboxMessages(authorization)).thenReturn(expected);
        mockMvc.perform(get("/api/v1/messages/inbox/all").contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", authorization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..toUsers").value("tosho"));
    }

    @Test
    @DisplayName("Positive test - get all new inbox messages for authorized user")
    void getInboxNew() throws Exception {
        when(messageService.getInboxNewMessages(authorization)).thenReturn(expected);
        mockMvc.perform(get("/api/v1/messages/inbox/new").contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", authorization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..toUsers").value("tosho"))
                .andExpect(jsonPath("$..isRead").value(false));

    }

    @Test
    @DisplayName("Positive test - get inbox message of authorized user")
    void getInboxMessage() throws Exception {
        expectedDto.setRead(true);
        when(messageService.openInboxMessage(1, authorization)).thenReturn(expectedDto);
        mockMvc.perform(get("/api/v1/messages/inbox/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", authorization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageId").value(1))
                .andExpect(jsonPath("$.isRead").value(true));
    }

    @Test
    @DisplayName("Negative test - try getting invalid inbox message of authorized user and return http 404")
    void getInboxMessageNotExisting() throws Exception {
        when(messageService.openInboxMessage(1000, authorization)).thenThrow(new MessageNotFoundException("Message not found in user inbox"));
        mockMvc.perform(get("/api/v1/messages/inbox/{id}", 1000).contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", authorization))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Message not found in user inbox"));
    }

    @Test
    @DisplayName("Negative test - try getting valid inbox message of unauthorized user")
    void getInboxMessageEmptyToken() throws Exception {
        mockMvc.perform(get("/api/v1/messages/inbox/{id}", 1000).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Authorization token is empty"));
    }

    @Test
    @DisplayName("Negative test - try to get message files with no token")
    void getFilesByMessageEmptyToken() throws Exception {
        mockMvc.perform(get("/api/v1/messages/{id}/files", 2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Authorization token is empty"));
    }

    @Test
    @DisplayName("Positive test - get all message files with valid token")
    void getAllFilesByMessage() throws Exception {
        FileGetDto fileGetDto = new FileGetDto();
        fileGetDto.setFileId(2L);
        fileGetDto.setFilePath("/dwsd/fwf");
        fileGetDto.setMessageId(message1.getMessageId());
        fileGetDto.setFileName("fwfw");
        List<FileGetDto> files = List.of(fileGetDto);

        when(messageService.findById(1)).thenReturn(Optional.ofNullable(message1));
        when(fileMapper.fileSetToFileGetDtoSet(message1.getFiles())).thenReturn(files);
        mockMvc.perform(get("/api/v1/messages/{id}/files", 1).contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", authorization))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Negative test - try to get all files of not existing message with valid token")
    void getAllFilesByInvalidMessage() throws Exception {
        mockMvc.perform(get("/api/v1/messages/{id}/files", 2).contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", authorization))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Message Not Found"));
    }

    @Test
    @DisplayName("Positive test - create message with valid token")
    void create() throws Exception {
        MessagePostDto messagePostDto = new MessagePostDto();
        messagePostDto.setText("dgd1");
        messagePostDto.setSubject("1gggggggg");
        messagePostDto.setToUsers("tosho");
        messagePostDto.setAttachments(Set.of(new String[]{"https://ibb.co/qxdfyT7"}));

        this.mockMvc.perform(post("/api/v1/messages")
                        .contentType(MediaType.APPLICATION_JSON).header("authorization", authorization).content(objectMapper.writeValueAsString(messagePostDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Negative test - try to create message with no token")
    void createNoToken() throws Exception {
        MessagePostDto messagePostDto = new MessagePostDto();
        messagePostDto.setText("text");
        messagePostDto.setSubject("subject");
        messagePostDto.setToUsers("tosho");
        messagePostDto.setAttachments(Set.of(new String[]{"https://ibb.co/qxdfyT7"}));

        this.mockMvc.perform(post("/api/v1/messages")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(messagePostDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Authorization token is empty"));
    }

    @Test
    @DisplayName("Negative test - try to create message with not setting non-null field")
    void createNullToUser() throws Exception {
        MessagePostDto messagePostDto = new MessagePostDto();
        messagePostDto.setText("text");
        messagePostDto.setSubject("subject");
        messagePostDto.setAttachments(Set.of(new String[]{"https://ibb.co/qxdfyT7"}));

        this.mockMvc.perform(post("/api/v1/messages")
                        .contentType(MediaType.APPLICATION_JSON).header("authorization", authorization).content(objectMapper.writeValueAsString(messagePostDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("toUsers is empty"));
    }

    @Test
    @DisplayName("Negative test - get message with valid id and no token")
    void getByIdEmptyToken() throws Exception {
        mockMvc.perform(get("/api/v1/messages/{id}", 2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Authorization token is empty"));
    }

    @Test
    @DisplayName("Positive test - get message with valid id and valid token")
    void getById() throws Exception {
        when(messageService.findById(1L)).thenReturn(Optional.of(message1));
        mockMvc.perform(get("/api/v1/messages/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", authorization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageId").value(1));
    }

    @Test
    @DisplayName("Negative test - get message with invalid id and valid token")
    void getByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/messages/{id}", 1000).contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", authorization))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Message Not Found"));
    }

    @Test
    @DisplayName("Positive test - update message with valid token")
    void update() throws Exception {
        MessagePostDto messagePostDto = new MessagePostDto();
        messagePostDto.setText("text");
        messagePostDto.setSubject("edited");
        messagePostDto.setToUsers("tosho");
        messagePostDto.setAttachments(Set.of(new String[]{"https://ibb.co/qxdfyT7"}));

        expectedDto.setSubject("edited");

        when(messageService.updateMessageByDto(1, messagePostDto, new HashSet<>())).thenReturn(expectedDto);
        this.mockMvc.perform(put("/api/v1/messages/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON).header("authorization", authorization).content(objectMapper.writeValueAsString(messagePostDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("edited"));
    }

    @Test
    @DisplayName("Positive test - delete message with valid id and valid token")
    void deleteExistingMessage() throws Exception {
        doNothing().when(messageService).delete(message1);
        this.mockMvc.perform(delete("/api/v1/messages/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON).header("authorization", authorization))
                .andExpect(status().isGone());
    }

    @Test
    @DisplayName("Negative test - try to delete message with invalid id and valid token")
    void deleteNotExistingMessage() throws Exception {
        doThrow(new MessageNotFoundException("Message Not Found")).when(messageService).deleteMessage(10000);
        this.mockMvc.perform(delete("/api/v1/messages/{id}", 10000)
                        .contentType(MediaType.APPLICATION_JSON).header("authorization", authorization))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Message Not Found"));
    }

    @Test
    @DisplayName("Negative test - try to delete message with valid id and invalid token")
    void deleteExistingMessageNoToken() throws Exception {
        this.mockMvc.perform(delete("/api/v1/messages/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Authorization token is empty"));
    }
}