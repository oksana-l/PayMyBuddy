package controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import com.PayMyBuddy.controller.TransferController;
import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.service.AccountService;
import com.PayMyBuddy.service.TransactionService;

@AutoConfigureMockMvc
@ContextConfiguration(classes = TransferController.class)
@WebMvcTest
public class TransferControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	@MockBean
	private TransactionService transactionService;
	private final static String TEST_USER_EMAIL = "stacy@test.com";

	@Test
	public void shouldShowTransferTest() throws Exception {
		Account account = new Account();

		Page<Transaction> page = new PageImpl<Transaction>(new ArrayList<Transaction>());

		when(accountService.findAccountByEmail(any())).thenReturn(account);
		when(transactionService.findTransactionsForPage(any(), any())).thenReturn(page);

		mockMvc.perform(get("/transfer")
				.with(user(TEST_USER_EMAIL)))
				.andExpect(status().isOk())
				.andExpect(view().name("transfer"))
				.andExpect(model().attributeExists("field"))
				.andExpect(model().attributeExists("sortDir"))
				.andExpect(model().attributeExists("currentPage"))
				.andExpect(model().attribute("totalPages", page.getTotalPages()));
	}
	
	@Test
	public void shouldSaveTransactionTest() throws Exception {
		Account account = new Account();
		BindingResult result = mock(BindingResult.class);
		Page<Transaction> page = new PageImpl<Transaction>(new ArrayList<Transaction>());

		when(accountService.findAccountByEmail(any())).thenReturn(account);
		when(accountService.userHasAmount(anyString(), any(BigDecimal.class)))
			.thenReturn(true);
		when(transactionService.findTransactionsForPage(any(), any())).thenReturn(page);
		when(result.hasErrors()).thenReturn(false);

		mockMvc.perform(post("/transfer")
				.with(csrf())
				.with(user(TEST_USER_EMAIL))
	            .param("action", "signup")
				.param("email", "alex@test.com"))
				.andExpect(status().is3xxRedirection());
		
		verify(transactionService, times(1)).saveTransaction(anyString(), any());
	}
	
	@Test
	public void shouldSaveTransactionIfHasAmountTest() throws Exception {
		Account account = new Account();
		BindingResult result = mock(BindingResult.class);
		Page<Transaction> page = new PageImpl<Transaction>(new ArrayList<Transaction>());

		when(accountService.findAccountByEmail(any())).thenReturn(account);
		when(accountService.userHasAmount(anyString(), any(BigDecimal.class)))
			.thenReturn(false);
		when(transactionService.findTransactionsForPage(any(), any())).thenReturn(page);
		when(result.hasErrors()).thenReturn(false);

		mockMvc.perform(post("/transfer")
				.with(csrf())
				.with(user(TEST_USER_EMAIL))
	            .param("action", "signup")
				.param("email", "alex@test.com"))
				.andExpect(status().isOk())
				.andExpect(view().name("transfer"))
				.andExpect(model().attributeExists("field"))
				.andExpect(model().attributeExists("sortDir"))
				.andExpect(model().attributeExists("currentPage"))
				.andExpect(model().attribute("totalPages", page.getTotalPages()))
				.andExpect(model().attributeHasFieldErrors("transaction", "amount"));
	}
	
	@Test
	public void shouldNotSaveTransactionTest() throws Exception {
		Account account = new Account();

		Page<Transaction> page = new PageImpl<Transaction>(new ArrayList<Transaction>());

		when(accountService.findAccountByEmail(any())).thenReturn(account);
		when(transactionService.findTransactionsForPage(any(), any())).thenReturn(page);

		mockMvc.perform(post("/transfer")
				.with(csrf())
				.with(user(TEST_USER_EMAIL))
	            .param("action", "signup")
				.param("email", "alex@test.com"))
				.andExpect(status().isOk())
				.andExpect(view().name("transfer"))
				.andExpect(model().attributeExists("field"))
				.andExpect(model().attributeExists("sortDir"))
				.andExpect(model().attributeExists("currentPage"))
				.andExpect(model().attribute("totalPages", page.getTotalPages()));
	}
}
