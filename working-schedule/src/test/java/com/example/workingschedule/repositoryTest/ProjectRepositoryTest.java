package com.example.workingschedule.repositoryTest;

import com.example.workingschedule.entity.Project;
import com.example.workingschedule.enums.ProjectType;
import com.example.workingschedule.repository.ProjectRepository;
import com.example.workingschedule.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.sql.DataSourceDefinition;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@DataJpaTest

public class ProjectRepositoryTest {
@Autowired
ProjectRepository projectRepository;


    @Test
    void getProjectByName(){
        Project project = new Project();
        project.setId(1);
        project.setName("Ina");
        List<Project> newProject = projectRepository.getProjectByName(project.getName());
        assertNotNull(project);
        assertEquals("Ina", project.getName());
    }

    @Test
    void getProjectByStatus(){
        Project project = new Project();
        project.setId(1);
        project.setStatus(ProjectType.RUNNING);
        List<Project> newProject = projectRepository.getProjectByStatus(project.getStatus());
        assertNotNull(project);
        assertEquals(ProjectType.RUNNING, project.getStatus());
    }

}
