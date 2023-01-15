package controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.PayMyBuddy.controller.LoginController;

@AutoConfigureMockMvc
@ContextConfiguration(classes = LoginController.class)
@WebMvcTest
public class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldFormLoginTest() throws Exception {
		mockMvc.perform(
				get("/login"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void shouldLoginTest( ) throws Exception {
		mockMvc.perform(
				formLogin("/login").user("admin").password("pass"))
				.andExpect(status().is3xxRedirection());
	}
}
