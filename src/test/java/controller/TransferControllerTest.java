package controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.PayMyBuddy.controller.TransferController;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.service.TransactionService;
import com.PayMyBuddy.service.UserService;

@AutoConfigureMockMvc
@ContextConfiguration(classes = TransferController.class)
@WebMvcTest
public class TransferControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private TransactionService transactionService;
	private final static String TEST_USER_ID = "user-id-123";
	
	@Test
	public void shouldShowTransferTest() throws Exception {
		Account account = new Account();
		Page<Transaction> page = new PageImpl<Transaction>(new ArrayList<Transaction>());
		when(userService.findAccountByEmail(any())).thenReturn(account);
		when(transactionService.findTransactionWithSorting(any(), any())).thenReturn(page);
        mockMvc.perform(get("/transfer")
        .with(user(TEST_USER_ID)))
        .andExpect(status().isOk());
	}
}
