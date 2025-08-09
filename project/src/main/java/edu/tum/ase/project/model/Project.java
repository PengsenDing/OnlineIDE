package edu.tum.ase.project.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "project_id")
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // ... additional members , often include @OneToMany mappings
    protected Project() {
        // no - args constructor required by JPA spec
        // this one is protected since it shouldn ’t be used directly
    }

    public Project(String name) {
        this.name = name;
    }

    // getters and setters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}