package com.example.workingschedule.controller;

import com.example.workingschedule.dto.ProjectDTO;
import com.example.workingschedule.entity.Project;
import com.example.workingschedule.enums.ProjectType;
import com.example.workingschedule.repository.ProjectRepository;
import com.example.workingschedule.service.ProjectService;
import com.example.workingschedule.service.UserService;
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
    private final UserService userService;
    private final ProjectRepository projectRepository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public ProjectController(ProjectService projectService, ProjectRepository projectRepository, UserService userService) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @GetMapping()
    private List<ProjectDTO> getAllProjects(@RequestHeader(name = "Authorization") String token) {
        return projectService.getAllProjects().stream().map(project -> mapper.map(project, ProjectDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{projectId}")
    private ResponseEntity<ProjectDTO> getProject(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "projectId") Integer projectId) {
        Project project = projectService.getProjectById(projectId);
        ProjectDTO projectDTO = mapper.map(project, ProjectDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(projectDTO);

    }

    @GetMapping("/status")
    private List<ProjectDTO> getProjectByStatus(@RequestHeader(name = "Authorization") String token, @RequestParam(value = "status") ProjectType status) {
        return projectService.getProjectByStatus(status).stream().map(project -> mapper.map(project, ProjectDTO.class)).collect(Collectors.toList());

    }

    @GetMapping("/name/{name}")
    private List<ProjectDTO> getProjectByName(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "name") String name) {
        return projectService.getProjectByName(name).stream().map(project -> mapper.map(project, ProjectDTO.class)).collect(Collectors.toList());

    }

    @DeleteMapping("/{projectId}")
    private void deleteProject(@RequestHeader(name = "Authorization") String token, @PathVariable Integer projectId) {
        projectService.deleteProject(projectId);
    }


    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "projectId") Integer projectId, @RequestBody Project project) {
        projectService.updateProject(token, projectId, project);
        return ResponseEntity.status(HttpStatus.OK).body(project);

    }

    @PostMapping()
    public ResponseEntity<ProjectDTO> saveProject(@RequestHeader(name = "Authorization") String token, @RequestBody ProjectDTO projectDTO) {

        Integer userId = userService.getUser(token).getId();
        projectDTO.setUserId(userId);
        Project project = new Project();
        project.setUserId(projectDTO.getUserId());
        project.setName(projectDTO.getName());
        project.setBudget(projectDTO.getBudget());
        project.setStatus(projectDTO.getStatus());
        project.setId(projectDTO.getId());
        project.setEndDate(projectDTO.getEndDate());
        project.setStartDate(projectDTO.getStartDate());
        projectService.saveProject(token, project);
        return ResponseEntity.status(HttpStatus.OK).body(projectDTO);


    }
}
