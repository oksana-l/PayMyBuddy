package controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.PayMyBuddy.configuration.SecurityConfiguration;
import com.PayMyBuddy.controller.AccountController;
import com.PayMyBuddy.model.dto.AccountDTO;
import com.PayMyBuddy.service.AccountService;
import com.PayMyBuddy.service.ConnectionService;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {AccountController.class, SecurityConfiguration.class})
@WebMvcTest(AccountController.class)
public class AccountControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AccountService accountService;
	
	@MockBean
	private ConnectionService connectionService;
	
	@Test
	public void shouldShowRegistrationFormTest() throws Exception {
		
		mockMvc.perform(
				get("/registration"))
        		.andExpect(status().isOk())
        		.andExpect(model().attributeExists("account"));
	}
	
	@Test
	public void shouldRegisterUserAccountTest() throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		final ArgumentCaptor<AccountDTO> accountDTOCaptor =
			    ArgumentCaptor.forClass(AccountDTO.class);
		
		when(accountService.ifUserExist(any())).thenReturn(false);

		mockMvc.perform(
        		MockMvcRequestBuilders.post("/registration")
				.param("password", "password")
				.param("userName", "Alex")
				.param("email", "alex@test.com"))
        		.andExpect(status().is(302))
        		.andExpect(redirectedUrl("/login"));
		
		verify(accountService).save(accountDTOCaptor.capture());
		Boolean result = passwordEncoder.matches("password", accountDTOCaptor
				.getValue().getPassword());
		
		Assertions.assertEquals("Alex", accountDTOCaptor.getValue().getUserName());
		Assertions.assertEquals("alex@test.com", accountDTOCaptor.getValue().getEmail());
		Assertions.assertTrue(result);
	}
	
	@Test
	public void shouldRegisterExistedUserAccountTest() throws Exception {
		AccountDTO accountDto = new AccountDTO();
		
		when(accountService.ifUserExist(any())).thenReturn(true);
		
		mockMvc.perform(
        		MockMvcRequestBuilders.post("/registration", accountDto))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("account", "email"));
	}
}
