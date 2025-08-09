package edu.tum.ase.compiler.service;

import edu.tum.ase.compiler.model.SourceCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CompilerServiceTests {

    private final CompilerService compilerService = new CompilerService();

    @Test
    void testCompileValidCode() {
        String validJavaCode = """
            public class Test {
                public static void main(String[] args) {
                    System.out.println("Hello, World!");
                }
            }
        """;

        SourceCode sourceCode = new SourceCode(validJavaCode, "Test.java");
        Map<String, Object> response = compilerService.compile(sourceCode);

        Assertions.assertTrue((boolean) response.get("compilable"), "The valid code should be compilable. Stderr: " + response.get("stderr"));
    }

    @Test
    void testCompileInvalidCode() {
        String code = "public class Main { public static void main(String[] args) { System.out.println(\"Hello, World!\"; } }";
        SourceCode sourceCode = new SourceCode("Main.java", code);

        Map<String, Object> result = compilerService.compile(sourceCode);

        assertFalse((boolean) result.get("compilable"));
        assertNotNull(result.get("stderr"));
    }

    @Test
    void testCompileWithException() {
        SourceCode sourceCode = new SourceCode("invalid/directory/Main.java", "public class Main {}");

        Map<String, Object> result = compilerService.compile(sourceCode);

        assertFalse((boolean) result.get("compilable"));
        assertNotNull(result.get("stderr"));
    }

}
