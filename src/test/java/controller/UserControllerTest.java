package controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.PayMyBuddy.controller.UserController;
import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.dto.UserDTO;
import com.PayMyBuddy.service.ConnectionService;
import com.PayMyBuddy.service.UserService;

@AutoConfigureMockMvc
@ContextConfiguration(classes = UserController.class)
@WebMvcTest
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	BCryptPasswordEncoder passwordEncoder;
	
	@MockBean
	private ConnectionService connectionService;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void shouldShowRegistrationFormTest() throws Exception {
		
		mockMvc.perform(get("/registration"))
        .andExpect(status().isOk());
	}
	
	@Test
	public void shouldRegisterUserAccountTest() throws Exception {
		Account account = new Account("Alex", "alex@rigolo.com", "password",
					null, null, null, null);
		UserDTO userDto = new UserDTO(account);
		
		when(userService.ifUserExist(userDto)).thenReturn(false);
		
        mockMvc.perform(
        		MockMvcRequestBuilders.post("/registration", userDto)
				.param("password", "password")
				.param("userName", "Alex")
				.param("email", "alex@rigolo.com"))
        		.andExpect(status().is(302))
        		.andExpect(redirectedUrl("/login"));
        
       // verify(userService).save(userDto); // potentiellement recuperer 
        								  // avec captor.getPassword mot de pass
        
	}
}
