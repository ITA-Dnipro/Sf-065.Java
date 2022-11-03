package com.example.workingschedule.service;

import com.example.workingschedule.entity.Project;
import com.example.workingschedule.enums.ProjectType;
import com.example.workingschedule.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;


    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();

    }

    public List<Project> getProjectByStatus(ProjectType status) {
        return projectRepository.getProjectByStatus(status);
    }

    public List<Project> getProjectByName(String name) {
        return projectRepository.getProjectByName(name);

    }

    public Project getProjectById(Integer id) {
        return projectRepository.findById(id).get();

    }

    public void deleteProject(Integer id) {
        projectRepository.deleteById(id);

    }

    public Project saveProject(@RequestHeader(name = "Authorization") String token, Project project) {
        return projectRepository.save(project);

    }

    public Project updateProject(@RequestHeader(name = "Authorization") String token, Integer id, Project projectRequest) {
        Project project = projectRepository.findById(id).orElseThrow();
        project.setBudget(projectRequest.getBudget());
        project.setName(projectRequest.getName());
        project.setStatus(projectRequest.getStatus());
        return projectRepository.save(project);

    }
}
