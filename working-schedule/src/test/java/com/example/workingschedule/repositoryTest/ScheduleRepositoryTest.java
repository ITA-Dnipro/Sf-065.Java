package com.example.workingschedule.repositoryTest;

import com.example.workingschedule.dto.ProjectDTO;
import com.example.workingschedule.dto.UserDTO;
import com.example.workingschedule.entity.Project;
import com.example.workingschedule.entity.Schedule;
import com.example.workingschedule.enums.ProjectType;
import com.example.workingschedule.repository.ProjectRepository;
import com.example.workingschedule.repository.ScheduleRepository;
import com.example.workingschedule.service.ProjectService;
import com.example.workingschedule.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@DataJpaTest
public class ScheduleRepositoryTest {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Test
    void getScheduleByUserId(){
        UserDTO user = new UserDTO();
        user.setId(1);
        Schedule schedule = new Schedule();
        schedule.setId(1);
        schedule.setUserId(user.getId());
        List<Schedule> newSchedule = scheduleRepository.getScheduleByUserId(schedule.getUserId());
        assertNotNull(schedule);
        assertEquals(1, schedule.getUserId());
    }
    @Test
    void getScheduleByProjectId(){
        ProjectDTO project = new ProjectDTO();
        project.setId(1);
        Schedule schedule = new Schedule();
        schedule.setId(1);
        schedule.setProjectId(project.getId());
        List<Schedule> newSchedule = scheduleRepository.getScheduleByProjectId(schedule.getProjectId());
        assertNotNull(schedule);
        assertEquals(1, schedule.getProjectId());
    }


}
