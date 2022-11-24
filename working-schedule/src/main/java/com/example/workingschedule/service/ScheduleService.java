package com.example.workingschedule.service;

import com.example.workingschedule.dto.ScheduleDTO;
import com.example.workingschedule.entity.Schedule;
import com.example.workingschedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;


@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;


    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, UserService userService) {
        this.scheduleRepository = scheduleRepository;
        this.userService = userService;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();

    }

    public Schedule getScheduleById(Integer id) {
        return scheduleRepository.findById(id).get();

    }

    public List<Schedule> getScheduleByUserId(Integer userId) {
        return scheduleRepository.getScheduleByUserId(userId);

    }

    public List<Schedule> getScheduleByProjectId(Integer projectId) {
        return scheduleRepository.getScheduleByProjectId(projectId);

    }

    public ScheduleDTO saveSchedule(@RequestHeader(name = "Authorization") String token, ScheduleDTO scheduleDTO) {
        Integer userId = userService.getUser(token).getId();
        scheduleDTO.setUserId(userId);
        Schedule schedule = new Schedule();
        schedule.setUserId(scheduleDTO.getUserId());
        schedule.setDateOfCreation(scheduleDTO.getDateOfCreation());
        schedule.setProjectId(scheduleDTO.getProjectId());
        schedule.setNumberOfHours(scheduleDTO.getNumberOfHours());
        scheduleRepository.save(schedule);
        scheduleDTO.setId(schedule.getId());
        return scheduleDTO;
    }

    public ScheduleDTO updateSchedule( Integer id, ScheduleDTO scheduleRequest) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow();
        schedule.setNumberOfHours(scheduleRequest.getNumberOfHours());
        schedule.setProjectId(scheduleRequest.getProjectId());
        schedule.setDateOfCreation(scheduleRequest.getDateOfCreation());
        scheduleRepository.save(schedule);
        return scheduleRequest;
    }

    public void deleteSchedule(Integer id) {
        scheduleRepository.deleteById(id);

    }
}
