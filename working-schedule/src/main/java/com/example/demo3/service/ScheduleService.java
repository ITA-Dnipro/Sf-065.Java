package com.example.demo3.service;
import com.example.demo3.entity.Schedule;
import com.example.demo3.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;


    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getAllSchedules() {
        return  scheduleRepository.findAll();

    }

    public Schedule getScheduleById(Integer id) {
        return scheduleRepository.findById(id).get();

    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);

    }

    public Schedule updateSchedule(Integer id, Schedule scheduleRequest) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow();
        schedule.setNumberOfHours(scheduleRequest.getNumberOfHours());
//        schedule.setProjectId(scheduleRequest.getProjectId());
        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return updatedSchedule;
    }

    public void deleteSchedule(Integer id) {
        scheduleRepository.deleteById(id);

    }
}