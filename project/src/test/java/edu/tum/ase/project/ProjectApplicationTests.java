package edu.tum.ase.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import edu.tum.ase.project.repository.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
class ProjectApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SourceFileRepository sourceFileRepository;


    @BeforeEach
    void setup() {
        projectRepository.deleteAll();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        sourceFileRepository.deleteAll();
    }


    @Test
    void contextLoads() {

    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void testCreateAndGetProject() throws Exception {
        mockMvc.perform(post("/api/projects/my-project")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("my-project"));

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void testCreateAndGetSourceFile() throws Exception {
        String projectName = "my-project";
        String fileName = "Main.java";

        mockMvc.perform(post("/api/projects/{name}", projectName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/sourceFile/project/{project_name}", projectName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fileName))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/sourceFile/project/{project_name}/{name}", projectName, fileName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(fileName));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void testCreateDuplicateProject() throws Exception {
        String projectName = "duplicate-project";

        mockMvc.perform(post("/api/projects/{name}", projectName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/projects/{name}", projectName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void testDeleteProject() throws Exception {
        String projectName = "delete-project";

        MvcResult createResult = mockMvc.perform(post("/api/projects/{name}", projectName))
                .andExpect(status().isOk())
                .andReturn();

        String projectJson = createResult.getResponse().getContentAsString();
        UUID projectId = UUID.fromString(JsonPath.read(projectJson, "$.id"));

        mockMvc.perform(delete("/api/projects/{id}", projectId))
                .andExpect(status().isOk());
    }

}
