package edu.tum.ase.compiler.controller;

import edu.tum.ase.compiler.model.SourceCode;
import edu.tum.ase.compiler.service.CompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/compile")
public class CompilerController {

    @Autowired
    private CompilerService compilerService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> compileCode(@RequestBody SourceCode sourceCode) {
        Map<String, Object> result = compilerService.compile(sourceCode);
        return ResponseEntity.ok(result);
    }
}
