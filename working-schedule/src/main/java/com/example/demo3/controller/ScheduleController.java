package com.example.demo3.controller;

import com.example.demo3.dto.ScheduleDTO;
import com.example.demo3.entity.Schedule;
import com.example.demo3.service.ScheduleService;
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
        private ModelMapper mapper = new ModelMapper();

        @Autowired
        public ScheduleController(ScheduleService scheduleService) {
            this.scheduleService = scheduleService;
        }

        @GetMapping()
        private List<ScheduleDTO> getAllSchedules() {
            return scheduleService.getAllSchedules().stream().map(schedule -> mapper.map(schedule, ScheduleDTO.class)).collect(Collectors.toList());
        }

        @GetMapping("/{scheduleId}")
        private ResponseEntity<ScheduleDTO> getSchedule(@PathVariable Integer scheduleId) {

            Schedule schedule = scheduleService.getScheduleById(scheduleId);
            ScheduleDTO scheduleDTO = mapper.map(schedule, ScheduleDTO.class);
            scheduleDTO.setProjectId(schedule.getProject().getId());
            return ResponseEntity.status(HttpStatus.OK).body(scheduleDTO);

        }

        @DeleteMapping("/{scheduleId}")
        private void deleteSchedule(@PathVariable Integer scheduleId) {
            scheduleService.deleteSchedule(scheduleId);
        }

        @PutMapping("/{scheduleId}")
        public ResponseEntity<ScheduleDTO> updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {
            Schedule scheduleRequest = mapper.map(scheduleDTO, Schedule.class);
//            Schedule schedule = scheduleService.updateSchedule(scheduleId, scheduleRequest);
            ScheduleDTO scheduleResponse = mapper.map(scheduleRequest, ScheduleDTO.class);
            return ResponseEntity.status(HttpStatus.OK).body(scheduleResponse);
        }

        @PostMapping()
        public ResponseEntity<ScheduleDTO> saveSchedule(@RequestBody ScheduleDTO scheduleDTO) {
            Schedule scheduleRequest = mapper.map(scheduleDTO, Schedule.class);
            Schedule schedule = scheduleService.saveSchedule(scheduleRequest);
            ScheduleDTO scheduleResponse = mapper.map(schedule, ScheduleDTO.class);
            return ResponseEntity.status(HttpStatus.OK).body(scheduleResponse);


        }


    }




