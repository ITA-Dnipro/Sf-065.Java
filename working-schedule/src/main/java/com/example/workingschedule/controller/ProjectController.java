package com.example.workingschedule.controller;


import com.example.workingschedule.dto.ProjectDTO;
import com.example.workingschedule.dto.UserDTO;
import com.example.workingschedule.entity.Project;
import com.example.workingschedule.repository.ProjectRepository;
import com.example.workingschedule.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public ProjectController(ProjectService projectService, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
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

    @DeleteMapping("/{projectId}")
    private void deleteProject(@PathVariable Integer projectId) {
        projectService.deleteProject(projectId);
    }


    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable("projectId") Integer id, @RequestBody Project project) {
        if (projectRepository.findById(id).isPresent()) {
            projectService.updateProject(id, project);
            return ResponseEntity.status(HttpStatus.OK).body(project);
        } else {
            throw new Error("No such element");
        }
    }

    @PostMapping()
    public ResponseEntity<ProjectDTO> saveProject(@RequestBody Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setEndDate(project.getEndDate());
        dto.setStartDate(project.getStartDate());
        dto.setStatus(project.getStatus());
        dto.setName(project.getName());
        UserDTO user = new UserDTO();
        dto.setUserId(user.getId());
        projectService.saveProject(project);
        return ResponseEntity.status(HttpStatus.OK).body(dto);


    }
}
