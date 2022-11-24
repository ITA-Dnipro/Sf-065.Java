package com.ssjavaacademy.www.messengerattachments.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssjavaacademy.www.messengerattachments.entities.File;
import com.ssjavaacademy.www.messengerattachments.entities.Message;
import com.ssjavaacademy.www.messengerattachments.services.FileService;
import com.ssjavaacademy.www.messengerattachments.services.MessageService;
import com.ssjavaacademy.www.messengerattachments.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class FileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    FileService fileService;

    @MockBean
    UserService userService;

    @MockBean
    MessageService messageService;

    @MockBean
    MessageController messageController;

    private File file1;
    private File file2;
    String authorization;

    @BeforeEach
    void setUp() {
        file1 = new File();
        file1.setFileId(1L);
        file1.setFilePath("/dwsd/fwf");
        file1.setMessageId(new Message());
        file1.setFileName("fwfw");

        file2 = new File();
        file2.setFileId(2L);
        file2.setFilePath("/dwsd/fwf");
        file2.setMessageId(new Message());
        file2.setFileName("fwfw");

        authorization = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoidG9zaG8iLCJleHAiOjE2Njg3MTg2NzYsImlhdCI6MTY2NzgxODY3Niwic2NvcGUiOiJST0xFX1VTRVIifQ.VnLUW0jhaEygpOcOplS7yWwTo1n4ywRSAKHkDt5YTSVSvgqBkJNSAhGB3SWyUf4-zm5TsM8Jmg5-QRB5JLOBBj_Bsu4Qd0cJXHRl20Nrr1hxNB3-NTCU05BxOTDcyOzbZ9oAzegleH6utCLDePKPPdHJJ_TT9ibT9qN4o_ZViQhRvo1KRxbiWjVK_jQYvGSJnX7CETpDhtsnvClbmfVm1kuIOv_gJKo38sLI8EDz5JrF7UAe6FjuZ83ok6hrdQlPeVzftdqN-IT1fxEB4pzIiseEbQcrIzfswFjZDx26MnfglmnKn8T5EeDVTsbVqTkiAudTKemamnUKeiAeIsCDuA";
    }

    @Test()
    @DisplayName("Positive test - getting all files in the system with valid bearer token")
    void getAllFilesWithToken() throws Exception {
        List<File> list = new ArrayList<>();
        list.add(file1);
        list.add(file2);

        when(fileService.findAll()).thenReturn(list);
        this.mockMvc.perform(get("/api/v1/files").accept(MediaType.APPLICATION_JSON).header("authorization", authorization))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$..[%s].fileId",0).value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$..[%s].fileId",1).value(2))
                .andExpect(jsonPath("$.*", hasSize(2)));

    }

    @Test
    @DisplayName("Negative test - getting all files in the system without bearer token")
    void getAllFilesWithNoToken() throws Exception {
        List<File> list = new ArrayList<>();
        list.add(file1);
        list.add(file2);

        when(fileService.findAll()).thenReturn(list);

        this.mockMvc.perform(get("/api/v1/files").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @DisplayName("Negative test - getting file with valid id without bearer token")
    void getByIdEmptyToken() throws Exception {
        mockMvc.perform(get("/api/v1/files/{id}",1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Authorization token is empty"));
    }

    @Test
    @DisplayName("Positive test - getting file by valid id in the system with bearer token")
    void getById() throws Exception {
        when(fileService.findById(1)).thenReturn(Optional.of(file1));
        mockMvc.perform(get("/api/v1/files/{id}",1).contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", authorization))
                .andExpect(status().isFound());
    }

    @Test
    @DisplayName("Negative test - getting file with invalid id with bearer token")
    void getByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/files/{id}",1000).contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", authorization))
                .andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("File Not Found"));
    }
}