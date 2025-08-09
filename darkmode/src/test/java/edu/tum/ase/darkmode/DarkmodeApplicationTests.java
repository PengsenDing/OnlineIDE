package edu.tum.ase.darkmode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DarkmodeApplicationTests {

	private MockMvc mockMvc;
	private DarkmodeApplication darkmodeApplication;

	@BeforeEach
	void setUp() {
		darkmodeApplication = new DarkmodeApplication();
		mockMvc = MockMvcBuilders.standaloneSetup(darkmodeApplication).build();
	}

	@Test
	void testInitialDarkModeStatus() throws Exception {
		mockMvc.perform(get("/dark-mode"))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}

	@Test
	void testToggleDarkMode() throws Exception {
		mockMvc.perform(get("/dark-mode/toggle"))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));

		Thread.sleep(3000);

		mockMvc.perform(get("/dark-mode/toggle"))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}

	@Test
	void testToggleCooldown() throws Exception {
		mockMvc.perform(get("/dark-mode/toggle"))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));

		mockMvc.perform(get("/dark-mode/toggle"))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));

		Thread.sleep(3000);

		mockMvc.perform(get("/dark-mode/toggle"))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}
}

