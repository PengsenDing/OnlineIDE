package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.SourceFile;
import edu.tum.ase.project.service.SourceFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sourceFile")
public class SourceFileController {

    private final SourceFileService sourceFileService;

    public SourceFileController(SourceFileService sourceFileService) {
        this.sourceFileService = sourceFileService;
    }

    @GetMapping("/project/{name}")
    public ResponseEntity<List<SourceFile>> getSourceFiles(@PathVariable("name") String project_name) {
        var sourceFiles = sourceFileService.getAllSourceFilesFromProject(project_name);
        return ResponseEntity.ok(sourceFiles);
    }

    @GetMapping("/project/{project_name}/{name}")
    public ResponseEntity<SourceFile> getSourceFile(@PathVariable("name") String name,
                                                    @PathVariable("project_name") String project_name) {
        var sourceFile = sourceFileService.findByName(name, project_name);
        return ResponseEntity.ok(sourceFile);
    }

    @PostMapping("/project/{project_name}")
    public ResponseEntity<SourceFile> createSourceFile(@PathVariable("project_name") String project_name,
                                                       @RequestBody String name) {
        try {
            var createdsourceFile = sourceFileService.createSourceFile(new SourceFile(name, null, project_name));
            return ResponseEntity.ok(createdsourceFile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/sourceFiles/{id}")
    public ResponseEntity<SourceFile> updateSourceFile(@PathVariable UUID id,
    @RequestBody(required = false) String code) {
        var sourceFile = sourceFileService.updatSourceFile(id, code);
        return ResponseEntity.ok(sourceFile);
    }

    @DeleteMapping("/sourceFiles/{id}")
    public ResponseEntity<UUID> deleteSourceFile(@PathVariable UUID id) {
        sourceFileService.deleteSourceFile(id);
        return ResponseEntity.ok(id);
    }

}
