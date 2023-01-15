package controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.PayMyBuddy.controller.ContactController;

@AutoConfigureMockMvc
@ContextConfiguration(classes = ContactController.class)
@WebMvcTest
public class ContactControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final static String TEST_USER_ID = "user-id-123";

	@Test
	public void shouldContactTest() throws Exception {
		mockMvc.perform(
				get("/contact")
				.with(user(TEST_USER_ID)))
				.andExpect(status().isOk());
	}
}
