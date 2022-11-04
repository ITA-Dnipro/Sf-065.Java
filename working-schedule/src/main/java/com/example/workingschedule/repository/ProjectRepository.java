package com.example.workingschedule.repository;

import com.example.workingschedule.entity.Project;
import com.example.workingschedule.enums.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> getProjectByName(String name);

    List<Project> getProjectByStatus(ProjectType status);
}
