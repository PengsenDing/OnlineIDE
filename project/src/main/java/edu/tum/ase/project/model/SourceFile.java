package edu.tum.ase.project.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "projects_source_files")
public class SourceFile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "source_file_id")
    private UUID id;

    @Column(name = "name", nullable = false, unique = false)
    private String name;

    @Column(name = "code", length = 1000)
    private String code;

    @Column(name = "project_name", nullable = false)
    private String project_name;

    // ... additional members , often include @OneToMany mappings
    protected SourceFile() {
        // no - args constructor required by JPA spec
        // this one is protected since it shouldn ’t be used directly
    }

    public SourceFile(String name, String code, String project_name) {
        this.name = name;
        this.code = code;
        this.project_name = project_name;
    }

    // getters and setters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getProjectName() {
        return project_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setProjectName(String project_name) {
        this.project_name = project_name;
    }
}