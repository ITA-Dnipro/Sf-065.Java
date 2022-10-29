package com.example.workingschedule.service;


import com.example.workingschedule.entity.Project;
import com.example.workingschedule.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;


    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List <Project> getAllProjects() {
        return projectRepository.findAll();

    }

    public Project getProjectById(Integer id) {
        return projectRepository.findById(id).get();

    }
    public void deleteProject(Integer id) {
        projectRepository.deleteById(id);

    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);

    }

    public Project updateProject(Integer id, Project projectRequest) {
        Project project = projectRepository.findById(id).orElseThrow();
        project.setBudget(projectRequest.getBudget());
        project.setName(projectRequest.getName());
        project.setStatus(projectRequest.getStatus());
        project.setId(projectRequest.getId());
        project.setUserId(projectRequest.getUserId());
        projectRepository.save(project);
        return project;
    }
}
