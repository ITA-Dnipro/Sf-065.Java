package com.example.workingschedule.controller;

import com.example.workingschedule.dto.ScheduleDTO;
import com.example.workingschedule.entity.Schedule;
import com.example.workingschedule.exception.InvalidTokenException;
import com.example.workingschedule.repository.ScheduleRepository;
import com.example.workingschedule.service.ScheduleService;
import com.example.workingschedule.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;




@SecurityRequirement(name = "Authorization")
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

    @Operation(summary = "Get all schedules", description = "Returns all schedules")
    @GetMapping()
    private List<ScheduleDTO> getAllSchedules(@RequestHeader(name = "Authorization") String token) throws InvalidTokenException {
        userService.validateUser(token);
        return scheduleService.getAllSchedules().stream().map(schedule -> mapper.map(schedule, ScheduleDTO.class)).collect(Collectors.toList());
    }




    @Operation(summary = "Get a schedule by id", description = "Returns a schedule by specified id")
    @GetMapping("/{scheduleId}")
    private ResponseEntity<ScheduleDTO> getSchedule(@RequestHeader(name = "Authorization") String token, @PathVariable Integer scheduleId) throws InvalidTokenException {
        userService.validateUser(token);
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        ScheduleDTO scheduleDTO = mapper.map(schedule, ScheduleDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDTO);

    }

    @Operation(summary = "Get schedules by id of the employee", description = "Returns the schedules of a specified user")
    @GetMapping("/userId/{userId}")
    private List<ScheduleDTO> getScheduleByUserId(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "userId") Integer userId) throws InvalidTokenException {
        userService.validateUser(token);
        return scheduleService.getScheduleByUserId(userId).stream().map(schedule -> mapper.map(schedule, ScheduleDTO.class)).collect(Collectors.toList());

    }






        @Operation(summary = "Get all schedules related to a project", description = "Returns all schedules of a specified project")
    @GetMapping("/projectId/{projectId}")
    private List<ScheduleDTO> getScheduleByProjectId(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "projectId") Integer projectId) throws InvalidTokenException {
        userService.validateUser(token);
        return scheduleService.getScheduleByProjectId(projectId).stream().map(schedule -> mapper.map(schedule, ScheduleDTO.class)).collect(Collectors.toList());
    }





    @Operation(summary = "Delete a schedule", description = "Deletes a schedule by specified id")
    @DeleteMapping("/{scheduleId}")
    private void deleteSchedule(@RequestHeader(name = "Authorization") String token, @PathVariable Integer scheduleId) throws InvalidTokenException {
        userService.validateUser(token);
        scheduleService.deleteSchedule(scheduleId);
    }

    @Operation(summary = "Change a schedule", description = "Updates a schedule by specified id")
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDTO> updateSchedule(@RequestHeader(name = "Authorization") String token, @PathVariable("scheduleId") Integer id, @RequestBody ScheduleDTO scheduleDTO) throws InvalidTokenException {
        userService.validateUser(token);
        scheduleService.updateSchedule(id, scheduleDTO);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDTO);

    }

    @Operation(summary = "Create a schedule", description = "Inserts new schedule with given parameters")
    @PostMapping()
    public ResponseEntity<ScheduleDTO> saveSchedule(@RequestHeader(name = "Authorization") String token, @RequestBody ScheduleDTO scheduleDTO) throws InvalidTokenException {
        userService.validateUser(token);
        scheduleDTO = scheduleService.saveSchedule(token, scheduleDTO);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleDTO);


    }


}




