package com.example.workingschedule.controllerTest;


import com.example.workingschedule.controller.ScheduleController;
import com.example.workingschedule.dto.ProjectDTO;
import com.example.workingschedule.dto.ScheduleDTO;
import com.example.workingschedule.entity.Schedule;
import com.example.workingschedule.repository.ScheduleRepository;
import com.example.workingschedule.service.ScheduleService;
import com.example.workingschedule.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScheduleController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc

public class ScheduleControllerTest {
    @Autowired
    ScheduleController scheduleController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ScheduleRepository scheduleRepository;
    @MockBean
    ScheduleService scheduleService;
    @MockBean
    UserService userService;
    @Mock
    ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scheduleService = new ScheduleService(scheduleRepository, userService);
    }


    @Test
    void shouldFetchAllProjects() throws Exception {

        ScheduleDTO one = new ScheduleDTO();
        one.setId(1);
        ScheduleDTO two = new ScheduleDTO();
        two.setId(2);

        List<ScheduleDTO> expected = new ArrayList<>();
        expected.add(one);
        expected.add(one);


        RequestBuilder request = MockMvcRequestBuilders
                .get("/schedules")
                .header("Authorization", "asd");
        when(scheduleService.getAllSchedules().stream().map(schedule -> mapper.map(schedule, ScheduleDTO.class)).collect(Collectors.toList())).thenReturn(expected);
        this.mockMvc.perform(request)
                .andExpect(status().isOk());
    }





}
