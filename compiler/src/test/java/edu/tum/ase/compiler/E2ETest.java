package edu.tum.ase.compiler.e2e;

import edu.tum.ase.compiler.model.SourceCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class E2ETest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testValidCodeCompilation() throws Exception {
        String sourceCodeJson = """
            {
              "fileName": "Main.java",
              "code": "public class Main { public static void main(String[] args) { System.out.println(\\"Hello, World!\\"); }}"
            }
            """;

        MvcResult result = mockMvc.perform(post("/compile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceCodeJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        assertThat(responseJson).contains("\"compilable\":true");
    }

    @Test
    public void testInvalidCodeCompilation() throws Exception {
        String sourceCodeJson = """
        {
          "fileName": "Main.java",
          "code": "public class Main { public static void main(String[] args) { System.out.println(\\\"Hello, World!\\\") }"
        }
        """;

        MvcResult result = mockMvc.perform(post("/compile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceCodeJson))
                .andReturn();

        int status = result.getResponse().getStatus();
        String responseJson = result.getResponse().getContentAsString();

        assertThat(status).isEqualTo(200);
        assertThat(responseJson).contains("\"compilable\":false");
        assertThat(responseJson).contains("\"stderr\"");
    }


}
