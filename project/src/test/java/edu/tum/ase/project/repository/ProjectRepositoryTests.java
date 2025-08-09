package edu.tum.ase.project.repository;

import edu.tum.ase.project.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    void setup() {
        projectRepository.deleteAll();
    }

    @Test
    void testSaveAndFindByName() {
        String projectName = "Test Project";
        Project project = new Project(projectName);

        Project savedProject = projectRepository.save(project);
        Project foundProject = projectRepository.findByName(projectName);

        assertNotNull(savedProject.getId(), "Saved project should have an ID");
        assertNotNull(foundProject, "Project should be found by name");
        assertEquals(projectName, foundProject.getName(), "Project name should match");
    }

    @Test
    void testFindAllProjects() {
        Project project1 = new Project("Project 1");
        Project project2 = new Project("Project 2");

        projectRepository.save(project1);
        projectRepository.save(project2);

        List<Project> projects = projectRepository.findAll();

        assertEquals(2, projects.size(), "There should be two projects in the repository");
    }

    @Test
    void testDeleteProject() {
        Project project = new Project("Delete Project");
        Project savedProject = projectRepository.save(project);
        UUID projectId = savedProject.getId();

        projectRepository.deleteById(projectId);
        Optional<Project> deletedProject = projectRepository.findById(projectId);

        assertFalse(deletedProject.isPresent(), "The project should be deleted");
    }

    @Test
    void testUniqueConstraintViolation() {
        String projectName = "Unique Project";
        Project project1 = new Project(projectName);
        Project project2 = new Project(projectName);

        projectRepository.save(project1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            projectRepository.save(project2);
        }, "Saving a project with a duplicate name should throw a DataIntegrityViolationException");
    }
}
