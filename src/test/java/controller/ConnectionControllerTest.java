package controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.PayMyBuddy.controller.ConnectionController;
import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.service.ConnectionService;
import com.PayMyBuddy.service.UserService;

@AutoConfigureMockMvc
@ContextConfiguration(classes = ConnectionController.class)
@WebMvcTest
public class ConnectionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired 
	private ConnectionController connectionController;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private ConnectionService connectionService;
	private final static String TEST_USER_ID = "user-id-123";
	
	@Test
	void contextLoads() {
		assertThat(connectionController).isNotNull();
	}
	
	@Test
	public void shouldShowFormAddConnectionTest() throws Exception {
		Account account = new Account();
		when(userService.findAccountByEmail(any())).thenReturn(account);
        mockMvc.perform(get("/myConnections")
        .with(user(TEST_USER_ID)))
        .andExpect(status().isOk());
	}
}
