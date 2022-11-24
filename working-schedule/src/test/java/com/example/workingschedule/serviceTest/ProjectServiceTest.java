package com.example.workingschedule.serviceTest;

import com.example.workingschedule.dto.ProjectDTO;
import com.example.workingschedule.dto.UserDTO;
import com.example.workingschedule.entity.Project;
import com.example.workingschedule.enums.ProjectType;
import com.example.workingschedule.exception.InvalidTokenException;
import com.example.workingschedule.repository.ProjectRepository;
import com.example.workingschedule.service.ProjectService;

import com.example.workingschedule.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import javax.annotation.sql.DataSourceDefinition;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(ProjectService.class)
@ExtendWith(MockitoExtension.class)

public class ProjectServiceTest {

    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private ProjectService projectService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projectService = new ProjectService(projectRepository,userService);
    }



    @Test
    void save() throws InvalidTokenException {
        ProjectDTO project = new ProjectDTO();
        project.setId(1);
        project.setBudget(10);
        project.setName("ina");
        project.setStatus(ProjectType.valueOf("REJECTED"));


        Project entity = new Project();
        entity.setId(project.getId());
        entity.setStatus(project.getStatus());
        entity.setName(project.getName());
        entity.setBudget(project.getBudget());




        UserDTO user = new UserDTO();
        user.setId(1);
        when(userService.getUser("token")).thenReturn(user);
        when(projectRepository.save(any(Project.class))).thenReturn(entity);
        when(projectService.saveProject("token",project)).thenReturn(project);
        assertEquals("ina", (entity.getName()));

    }

    @Test
    void getAllProjects(){
        List<Project> list = new ArrayList<>();
        Project project = new Project();
        project.setId(1);
        project.setStatus(ProjectType.valueOf("RUNNING"));
        project.setName("ne znam");
        project.setBudget(123);
        Project proj = new Project();
        proj.setId(2);
        proj.setStatus(ProjectType.valueOf("RUNNING"));
        proj.setName("ne znam1");
        project.setBudget(1234);
        list.add(proj);
        list.add(project);
        when(projectRepository.findAll()).thenReturn(list);

        List <Project> projects = projectService.getAllProjects();
        assertEquals(2, projects.size());
        assertNotNull(projects);
    }

    @Test
    void getProjectById(){
        Project project = new Project();
        project.setId(1);
        when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project));
        Project existingProject = projectService.getProjectById(project.getId());
        assertNotNull(existingProject);
        assertThat(existingProject.getId()).isNotEqualTo(null);
    }
}
