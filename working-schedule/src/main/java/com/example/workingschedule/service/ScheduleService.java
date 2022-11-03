package com.example.workingschedule.service;

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

    public Schedule saveSchedule(String token, Schedule schedule) {
        return scheduleRepository.save(schedule);

    }

    public Schedule updateSchedule(@RequestHeader(name = "Authorization") String token, Integer id, Schedule scheduleRequest) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow();
        schedule.setNumberOfHours(scheduleRequest.getNumberOfHours());
        schedule.setProjectId(scheduleRequest.getProjectId());
        schedule.setDateOfCreation(scheduleRequest.getDateOfCreation());
        return scheduleRepository.save(schedule);

    }

    public void deleteSchedule(Integer id) {
        scheduleRepository.deleteById(id);

    }
}
