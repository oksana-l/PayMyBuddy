package controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import com.PayMyBuddy.configuration.SecurityConfiguration;
import com.PayMyBuddy.controller.ConnectionController;
import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.service.AccountService;
import com.PayMyBuddy.service.ConnectionService;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { ConnectionController.class, SecurityConfiguration.class })
@WebMvcTest
@WithMockUser(username = "alex@test.com")
public class ConnectionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;
	@MockBean
	private ConnectionService connectionService;
	private final static String TEST_USER_EMAIL = "stacy@test.com";

	/**
	 * Test de la méthode GET : affichage de la page de Connexions 
	 * avec son formulaire et la liste de connexions existantes 
	 * avec pagination
	 * @throws Exception
	 */
	@Test
	public void shouldShowFormAddConnectionTest() throws Exception {

		Account account = new Account();
		Page<Account> page = new PageImpl<Account>(new ArrayList<>());

		when(accountService.findAccountByEmail(any())).thenReturn(account);
		when(connectionService.findConnections(any(), any())).thenReturn(page);

		mockMvc.perform(get("/myConnections")
				.with(user(TEST_USER_EMAIL)))
				.andExpect(status().isOk())
				.andExpect(view().name("myConnections"))
				.andExpect(model().attributeExists("field"))
				.andExpect(model().attributeExists("sortDir"))
				.andExpect(model().attributeExists("currentPage"))
				.andExpect(model().attribute("totalPages", page.getTotalPages()));

	}
	
	/**
	 * Test de la méthode POST : recharger la page avec le formulaire vide
	 * @throws Exception
	 */
	@Test
	public void shouldNotAddConnectionTest() throws Exception {
		
		Account account = new Account("Alex", "alex@test.com", "password", null, 
				null, null, null);
		Page<Account> page = new PageImpl<Account>(new ArrayList<>());

		when(accountService.findAccountByEmail(any())).thenReturn(account);
		when(connectionService.findConnections(any(), any())).thenReturn(page);
		when(connectionService.save(any(), any())).thenReturn(null);
		when(connectionService.isAccountExists(any())).thenReturn(true);

		mockMvc.perform(post("/myConnections")
				.with(user(account.getEmail()))
				.param("email", "alex@test.com"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("connections"))
				.andExpect(model().attributeExists("connection"))
				.andExpect(model().attributeExists("currentPage"))
				.andExpect(model().attributeExists("totalItems"))
				.andExpect(model().attributeExists("field"))
				.andExpect(model().attributeExists("sortDir"))
				.andExpect(model().attribute("totalPages", page.getTotalPages()));
	}
	
	@Test
	public void shouldAddConnectionIfAccountNotExistsTest() throws Exception {

		Account account = new Account();
		Page<Account> page = new PageImpl<Account>(new ArrayList<>());
		
		when(accountService.findAccountByEmail(any())).thenReturn(account);
		when(connectionService.isAccountExists(any())).thenReturn(false);
		when(connectionService.findConnections(any(), any())).thenReturn(page);
		
		mockMvc.perform(post("/myConnections", "alex@test.com")
				.with(user(TEST_USER_EMAIL)))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("connection", "email"));
	}
	
	@Test
	public void shouldAddConnectionIfConnectedAccountExistsTest() throws Exception {

		Account account = new Account();
		Page<Account> page = new PageImpl<Account>(new ArrayList<>());
		
		when(accountService.findAccountByEmail(any())).thenReturn(account);
		when(connectionService.isConnectedAccountExists(any(), eq(TEST_USER_EMAIL))).thenReturn(true);
		when(connectionService.findConnections(any(), any())).thenReturn(page);
		
		mockMvc.perform(post("/myConnections")
				.with(user(TEST_USER_EMAIL))
				.param("email", "alex@test.com"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("connection", "email"))
				.andExpect(model().attributeExists("connections"))
				.andExpect(model().attributeExists("connection"))
				.andExpect(model().attributeExists("currentPage"))
				.andExpect(model().attributeExists("totalItems"))
				.andExpect(model().attributeExists("field"))
				.andExpect(model().attributeExists("sortDir"))
				.andExpect(model().attribute("totalPages", page.getTotalPages()));
	}
	
	@Test
	public void shouldAddConnectionTest() throws Exception {
		
		Account connection = new Account("Stacy", "stacy@test.com", null, null, null, null, null);
		Set<Account> connections = new HashSet<Account>();
		connections.add(connection);
		Account account = new Account("Alex", "alex@test.com", "password", null, 
				connections, null, null);
		BindingResult result = mock(BindingResult.class);
		Page<Account> page = new PageImpl<Account>(new ArrayList<>());

		when(accountService.findAccountByEmail(any())).thenReturn(account);
		when(connectionService.findConnections(any(), any())).thenReturn(page);
		when(connectionService.save(any(), any())).thenReturn(connection);
		when(connectionService.isAccountExists(any())).thenReturn(true);
		when(connectionService.isConnectedAccountExists(anyString(), anyString())).thenReturn(false);
		when(result.hasErrors()).thenReturn(false);
		
		mockMvc.perform(post("/myConnections")
				.with(user(account.getEmail()))
				.param("email", "stacy@test.com"))
				.andExpect(status().is3xxRedirection());
		
		verify(connectionService).save(any(), any());
		verify(connectionService, times(1)).save(any(), any());
	}

}
