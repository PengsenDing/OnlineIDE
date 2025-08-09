package edu.tum.ase.project.service;

import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProject_Success() {
        String projectName = "Unique Project";
        Project project = new Project(projectName);

        when(projectRepository.findByName(projectName)).thenReturn(null);
        when(projectRepository.save(project)).thenReturn(project);

        Project createdProject = projectService.createProject(project);

        assertNotNull(createdProject);
        assertEquals(projectName, createdProject.getName());
        verify(projectRepository, times(1)).findByName(projectName);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void createProject_DuplicateName_ThrowsException() {
        String projectName = "Duplicate Project";
        Project project = new Project(projectName);

        when(projectRepository.findByName(projectName)).thenReturn(project);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> projectService.createProject(project)
        );

        assertEquals("Project with name " + projectName + " already exists.", exception.getMessage());
        verify(projectRepository, times(1)).findByName(projectName);
        verify(projectRepository, never()).save(any());
    }

    @Test
    void findByName_ReturnsProject() {
        String projectName = "Existing Project";
        Project project = new Project(projectName);

        when(projectRepository.findByName(projectName)).thenReturn(project);

        Project foundProject = projectService.findByName(projectName);

        assertNotNull(foundProject);
        assertEquals(projectName, foundProject.getName());
        verify(projectRepository, times(1)).findByName(projectName);
    }

    @Test
    void getProjects_ReturnsAllProjects() {
        List<Project> mockProjects = List.of(
                new Project("Project 1"),
                new Project("Project 2")
        );

        when(projectRepository.findAll()).thenReturn(mockProjects);

        List<Project> projects = projectService.getProjects();

        assertNotNull(projects);
        assertEquals(2, projects.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void deleteProject_CallsRepository() {
        UUID projectId = UUID.randomUUID();

        doNothing().when(projectRepository).deleteById(projectId);

        projectService.deleteProject(projectId);

        verify(projectRepository, times(1)).deleteById(projectId);
    }
}
