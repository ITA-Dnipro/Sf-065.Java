package com.example.demo3.service;

import com.example.demo3.entity.Project;
import com.example.demo3.repository.ProjectRepository;
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
}
