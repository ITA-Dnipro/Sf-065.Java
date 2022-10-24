package com.example.demo3.controller;

import com.example.demo3.dto.ProjectDTO;
import com.example.demo3.entity.Project;
import com.example.demo3.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    private List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects().stream().map(project -> mapper.map(project, ProjectDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{projectId}")
    private ResponseEntity<ProjectDTO> getProject(@PathVariable Integer projectId) {

      Project project = projectService.getProjectById(projectId);
        ProjectDTO projectDTO = mapper.map(project, ProjectDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(projectDTO);

    }
}
