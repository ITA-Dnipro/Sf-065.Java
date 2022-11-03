package com.example.workingschedule.controller;

import com.example.workingschedule.dto.ScheduleDTO;
import com.example.workingschedule.entity.Schedule;
import com.example.workingschedule.repository.ScheduleRepository;
import com.example.workingschedule.service.ScheduleService;
import com.example.workingschedule.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public ScheduleController(ScheduleService scheduleService, ScheduleRepository scheduleRepository, UserService userService) {
        this.scheduleService = scheduleService;
        this.scheduleRepository = scheduleRepository;
        this.userService = userService;
    }

    @GetMapping()
    private List<ScheduleDTO> getAllSchedules(@RequestHeader(name = "Authorization") String token) {
        return scheduleService.getAllSchedules().stream().map(schedule -> mapper.map(schedule, ScheduleDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{scheduleId}")
    private ResponseEntity<ScheduleDTO> getSchedule(@RequestHeader(name = "Authorization") String token, @PathVariable Integer scheduleId) {
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        ScheduleDTO scheduleDTO = mapper.map(schedule, ScheduleDTO.class);
        scheduleDTO.setProjectId(schedule.getProjectId());
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDTO);

    }


    @GetMapping("/userId/{userId}")
    private List<ScheduleDTO> getScheduleByUserId(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "userId") Integer userId) {
        return scheduleService.getScheduleByUserId(userId).stream().map(schedule -> mapper.map(schedule, ScheduleDTO.class)).collect(Collectors.toList());

    }


    @GetMapping("/projectId/{projectId}")
    private List<ScheduleDTO> getScheduleByProjectId(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "projectId") Integer projectId) {
        return scheduleService.getScheduleByProjectId(projectId).stream().map(schedule -> mapper.map(schedule, ScheduleDTO.class)).collect(Collectors.toList());
    }


    @DeleteMapping("/{scheduleId}")
    private void deleteSchedule(@RequestHeader(name = "Authorization") String token, @PathVariable Integer scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<Schedule> updateSchedule(@RequestHeader(name = "Authorization") String token, @PathVariable("scheduleId") Integer id, @RequestBody Schedule schedule) {
        scheduleService.updateSchedule(token, id, schedule);
        return ResponseEntity.status(HttpStatus.OK).body(schedule);

    }


    @PostMapping()
    public ResponseEntity<ScheduleDTO> saveSchedule(@RequestHeader(name = "Authorization") String token, @RequestBody ScheduleDTO scheduleDTO) {
        Integer userId = userService.getUser(token).getId();
        scheduleDTO.setUserId(userId);
        Schedule schedule = new Schedule();
        schedule.setDateOfCreation(scheduleDTO.getDateOfCreation());
        schedule.setId(scheduleDTO.getId());
        schedule.setUserId(scheduleDTO.getUserId());
        schedule.setProjectId(scheduleDTO.getProjectId());
        schedule.setNumberOfHours(scheduleDTO.getNumberOfHours());
        scheduleService.saveSchedule(token, schedule);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDTO);


    }


}




