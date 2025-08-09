package edu.tum.ase.project.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import edu.tum.ase.project.service.ProjectService;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;

import java.util.List;

import edu.tum.ase.project.model.Project;

import static org.mockito.Mockito.when;

import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class ProjectControllerTests {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @Test
    void testGetProjects() {
        List<Project> mockProjects = List.of(new Project("test-project"));
        when(projectService.getProjects()).thenReturn(mockProjects);

        ResponseEntity<List<Project>> response = projectController.getProjects();

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("test-project", response.getBody().get(0).getName());
    }

    @Test
    void testCreateProject() {
        Project mockProject = new Project("new-project");
        when(projectService.createProject(any(Project.class))).thenReturn(mockProject);

        ResponseEntity<Project> response = projectController.createProject("new-project");

        assertNotNull(response.getBody());
        assertEquals("new-project", response.getBody().getName());
    }
}
