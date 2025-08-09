package edu.tum.ase.compiler.controller;

import edu.tum.ase.compiler.model.SourceCode;
import edu.tum.ase.compiler.service.CompilerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompilerController.class)
class CompilerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompilerService compilerService;

    @Test
    void testCompileCode() throws Exception {
        Map<String, Object> mockResponse = Map.of(
                "success", true,
                "output", "Hello, World!"
        );
        Mockito.when(compilerService.compile(any(SourceCode.class))).thenReturn(mockResponse);

        // JSON request payload
        String requestPayload = """
                {
                    "language": "java",
                    "code": "public class Main { public static void main(String[] args) { System.out.println(\\"Hello, World!\\"); } }"
                }
                """;

        // Expected JSON response
        String expectedResponse = """
                {
                    "success": true,
                    "output": "Hello, World!"
                }
                """;

        mockMvc.perform(post("/compile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }
}
