package edu.tum.ase.project.service;

import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(Project project) {
        if (projectRepository.findByName(project.getName()) != null) {
            throw new IllegalArgumentException("Project with name " + project.getName() + " already exists.");
        }

        return projectRepository.save(project);
    }

    public Project findByName(String name) {
        return projectRepository.findByName(name);
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public void deleteProject(UUID id) {
        projectRepository.deleteById(id);
    }
}