package edu.tum.ase.project.service;

import edu.tum.ase.project.model.SourceFile;
import edu.tum.ase.project.repository.SourceFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SourceFileService {
    @Autowired
    private SourceFileRepository sourceFileRepository;

    public SourceFile createSourceFile(SourceFile sourceFile) {
        if (findByName(sourceFile.getName(), sourceFile.getProjectName()) != null) {
            throw new IllegalArgumentException("File with name " + sourceFile.getName() + " already exists.");
        }

        return sourceFileRepository.save(sourceFile);
    }

    public SourceFile updatSourceFile(UUID id, String code) {
        SourceFile file = sourceFileRepository.findById(id).orElse(null);

        if (file == null) {
            return file;
        }

        file.setCode(code);
        sourceFileRepository.save(file);
        return file;
    }

    public SourceFile findByName(String name, String projectName) {
        List<SourceFile> sourceFiles = getAllSourceFilesFromProject(projectName);
        System.out.println(sourceFiles);
        return sourceFiles.stream().filter(sourceFile -> sourceFile.getName().equals(name) && sourceFile.getProjectName().equals(projectName)).findFirst().orElse(null);
    }

    public List<SourceFile> getAllSourceFilesFromProject(String projectName) {
        List<SourceFile> files = sourceFileRepository.findAll();
        return files.stream().filter(sourceFile -> sourceFile.getProjectName().equals(projectName)).toList();
    }

    public void deleteSourceFile(UUID id) {
        sourceFileRepository.deleteById(id);
    }
}