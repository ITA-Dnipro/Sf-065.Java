package com.example.workingschedule.repository;

import com.example.workingschedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> getScheduleByProjectId(Integer projectId);

    List<Schedule> getScheduleByUserId(Integer userId);
}
