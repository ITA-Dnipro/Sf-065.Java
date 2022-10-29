package com.example.workingschedule.controller;

import com.example.workingschedule.service.ScheduleService;
import com.example.workingschedule.dto.ScheduleDTO;
import com.example.workingschedule.dto.UserDTO;
import com.example.workingschedule.entity.Schedule;
import com.example.workingschedule.repository.ScheduleRepository;
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
    private final ScheduleRepository scheduleRepository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public ScheduleController(ScheduleService scheduleService, ScheduleRepository scheduleRepository) {
        this.scheduleService = scheduleService;
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping()
    private List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules().stream().map(schedule -> mapper.map(schedule, ScheduleDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{scheduleId}")
    private ResponseEntity<ScheduleDTO> getSchedule(@PathVariable Integer scheduleId) {
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        ScheduleDTO scheduleDTO = mapper.map(schedule, ScheduleDTO.class);
        scheduleDTO.setProjectId(schedule.getProjectId());
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDTO);

    }

    @DeleteMapping("/{scheduleId}")
    private void deleteSchedule(@PathVariable Integer scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable("scheduleId") Integer id, @RequestBody Schedule schedule) {
        if (scheduleRepository.findById(id).isPresent()) {
            scheduleService.updateSchedule(id, schedule);
            return ResponseEntity.status(HttpStatus.OK).body(schedule);
        } else {
            throw new Error("No such element");
        }
    }

    @PostMapping()
    public ResponseEntity<ScheduleDTO> saveSchedule(@RequestBody Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setProjectId(schedule.getProjectId());
        dto.setNumberOfHours(schedule.getNumberOfHours());
        dto.setId(schedule.getId());
        UserDTO user = new UserDTO();
        dto.setUserId(user.getId());
        scheduleService.saveSchedule(schedule);
        return ResponseEntity.status(HttpStatus.OK).body(dto);


    }


}




