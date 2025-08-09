package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjects() {
        var projects = projectService.getProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Project> getProject(@PathVariable String name) {
        var project = projectService.findByName(name);
        return ResponseEntity.ok(project);
    }

    @PostMapping("/{name}")
    public ResponseEntity<Project> createProject(@PathVariable String name) {
        try {
            var createdProject = projectService.createProject(new Project(name));
            return ResponseEntity.ok(createdProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(id);
    }

}
