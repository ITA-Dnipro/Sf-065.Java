package com.example.workingschedule.serviceTest;

import com.example.workingschedule.dto.ScheduleDTO;
import com.example.workingschedule.entity.Schedule;

import com.example.workingschedule.repository.ScheduleRepository;

import com.example.workingschedule.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    void updateSchedule() {
        ScheduleDTO schedule = new ScheduleDTO();
        schedule.setId(1);
        schedule.setDateOfCreation(LocalDate.now());
        schedule.setNumberOfHours(2);
        schedule.setProjectId(1);
        schedule.setUserId(1);
        Schedule entity = new Schedule();
        entity.setId(schedule.getId());
        entity.setNumberOfHours(entity.getNumberOfHours());
        entity.setUserId(schedule.getUserId());
        entity.setProjectId(schedule.getProjectId());
        when(scheduleRepository.findById(anyInt())).thenReturn(Optional.of(entity));

        when(scheduleRepository.save(any(Schedule.class))).thenReturn(entity);
        ScheduleDTO exisitingSchedule = scheduleService.updateSchedule(entity.getId(),schedule);
        assertNotNull(exisitingSchedule);
        assertEquals(1, exisitingSchedule.getId());
    }
    @Test
    void deleteSchedule() {
        Schedule schedule = new Schedule();
        schedule.setId(1);
//        when(scheduleRepository.findById(anyInt())).thenReturn(Optional.of(schedule));
//        doNothing().when(scheduleRepository).delete(any(Schedule.class));

        scheduleService.deleteSchedule(schedule.getId());
        verify(scheduleRepository).deleteById(schedule.getId());



    }



}
