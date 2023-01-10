package controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.PayMyBuddy.configuration.SecurityConfiguration;
import com.PayMyBuddy.controller.ConnectionController;
import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.dto.ConnectionDTO;
import com.PayMyBuddy.service.AccountService;
import com.PayMyBuddy.service.ConnectionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {ConnectionController.class, SecurityConfiguration.class})
@WebMvcTest
@WithMockUser(username="alex@test.com")
public class ConnectionControllerTest {
	
    @Autowired
    private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;	

	@MockBean
	private AccountService accountService;	
	@MockBean
	private ConnectionService connectionService;
	
	@Test
	public void shouldShowFormAddConnectionTest() throws Exception {
		Account connection = new Account("Stacy", "stacy@test.com", 
				null, null, null, null, null);
		Set<Account> connections= new HashSet<Account>();
		connections.add(new Account(null, "stacy@test.com", 
				null, null, null, null, null));
		Account account = new Account("Alex", "alex@test.com", "password",
				null, connections, null, null);
		List<ConnectionDTO> listConnectionsDto = new ArrayList<>();
		listConnectionsDto.add(new ConnectionDTO(connection));
		
		when(accountService.findAccountByEmail(any())).thenReturn(account);
		
        mockMvc.perform(
        		MockMvcRequestBuilders.get("/myConnections")
        		.with(user(account.getEmail())))
        		.andExpect(status().isOk());
	}
	
	@Test
	public void shouldAddConnectionTest() throws Exception {
		Account connection = new Account("Stacy", "stacy@test.com", 
				null, null, null, null, null);
		Set<Account> connections= new HashSet<Account>();
		connections.add(connection);
		Account account = new Account("Alex", "alex@test.com", "password",
				null, connections, null, null);
		List<ConnectionDTO> listConnectionsDto = new ArrayList<>();
		listConnectionsDto.add(new ConnectionDTO(connection));
	
		when(accountService.findAccountByEmail(any())).thenReturn(account);
		
        mockMvc.perform(
        		MockMvcRequestBuilders.post("/myConnections")
        		.with(user(account.getEmail()))
        		.param("email", "alex@test.com"))
        		.andExpect(status().isOk())
        		.andExpect(model().attribute("connections", listConnectionsDto));
	}

}
