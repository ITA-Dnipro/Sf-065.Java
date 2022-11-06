package com.example.workingschedule.controller;

import com.example.workingschedule.dto.ProjectDTO;
import com.example.workingschedule.entity.Project;
import com.example.workingschedule.enums.ProjectType;
import com.example.workingschedule.exception.InvalidTokenException;
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
    private List<ProjectDTO> getAllProjects(@RequestHeader(name = "Authorization") String token) throws InvalidTokenException {
        userService.validateUser(token);
        return projectService.getAllProjects().stream().map(project -> mapper.map(project, ProjectDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{projectId}")
    private ResponseEntity<ProjectDTO> getProject(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "projectId") Integer projectId) throws InvalidTokenException {
        userService.validateUser(token);
        Project project = projectService.getProjectById(projectId);
        ProjectDTO projectDTO = mapper.map(project, ProjectDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(projectDTO);

    }

    @GetMapping("/status")
    private List<ProjectDTO> getProjectByStatus(@RequestHeader(name = "Authorization") String token, @RequestParam(value = "status") ProjectType status) throws InvalidTokenException {
        userService.validateUser(token);
        return projectService.getProjectByStatus(status).stream().map(project -> mapper.map(project, ProjectDTO.class)).collect(Collectors.toList());

    }

    @GetMapping("/name/{name}")
    private List<ProjectDTO> getProjectByName(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "name") String name) throws InvalidTokenException {
        userService.validateUser(token);
        return projectService.getProjectByName(name).stream().map(project -> mapper.map(project, ProjectDTO.class)).collect(Collectors.toList());

    }

    @DeleteMapping("/{projectId}")
    private void deleteProject(@RequestHeader(name = "Authorization") String token, @PathVariable Integer projectId) throws InvalidTokenException {
        userService.validateUser(token);
        projectService.deleteProject(projectId);
    }


    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@RequestHeader(name = "Authorization") String token, @PathVariable(value = "projectId") Integer projectId, @RequestBody ProjectDTO projectDTO) throws InvalidTokenException {
        userService.validateUser(token);
        projectService.updateProject(projectId, projectDTO);
        return ResponseEntity.status(HttpStatus.OK).body(projectDTO);

    }

    @PostMapping()
    public ResponseEntity<ProjectDTO> saveProject(@RequestHeader(name = "Authorization") String token, @RequestBody ProjectDTO projectDTO) throws InvalidTokenException {
        userService.validateUser(token);
        projectService.saveProject(token, projectDTO);
        return ResponseEntity.status(HttpStatus.OK).body(projectDTO);
    }
}
