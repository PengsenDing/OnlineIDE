package edu.tum.ase.project.model;

import edu.tum.ase.project.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import edu.tum.ase.project.ProjectApplication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ProjectApplication.class)
class ProjectTests {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void testSaveAndRetrieveProject() {
        String projectName = "Persisted Project";
        Project project = new Project(projectName);

        Project savedProject = projectRepository.save(project);
        assertNotNull(savedProject.getId());

        Optional<Project> retrievedProject = projectRepository.findById(savedProject.getId());
        assertTrue(retrievedProject.isPresent());
        assertEquals(projectName, retrievedProject.get().getName());
    }

    @Test
    void testUniqueConstraintViolation() {
        String projectName = "Unique Project";
        Project project1 = new Project(projectName);
        Project project2 = new Project(projectName);

        projectRepository.save(project1);
        assertThrows(Exception.class, () -> projectRepository.save(project2));
    }
}
