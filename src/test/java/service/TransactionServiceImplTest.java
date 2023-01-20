package service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.dto.ConnectionDTO;
import com.PayMyBuddy.model.dto.TransactionFormDTO;
import com.PayMyBuddy.model.dto.TransactionUserDTO;
import com.PayMyBuddy.model.exception.TransactionNotFoundException;
import com.PayMyBuddy.repository.AccountRepository;
import com.PayMyBuddy.repository.TransactionRepository;
import com.PayMyBuddy.service.AccountService;
import com.PayMyBuddy.service.AccountServiceImpl;
import com.PayMyBuddy.service.TransactionService;
import com.PayMyBuddy.service.TransactionServiceImpl;

public class TransactionServiceImplTest {

	private TransactionRepository transactionRepository;
	private AccountRepository accountRepository;
	private AccountService accountService;
	private TransactionService transactionService;
	private Account sender;
	private Account recepient;
	private Optional<Transaction> transaction;
	private List<Transaction> credits  = new ArrayList<Transaction>();
	private List<Transaction> debits = new ArrayList<Transaction>();
	private Set<Account> connections = new LinkedHashSet<>();
	
	@BeforeEach
	public void setUp() {
		transactionRepository = mock(TransactionRepository.class);
		accountRepository = mock(AccountRepository.class);
		accountService = mock(AccountServiceImpl.class);
		transactionService = new TransactionServiceImpl(
				transactionRepository, accountRepository, accountService);
		sender = new Account("Jhon", "jhon@moi.meme", "pass", new BigDecimal(0.00), 
				connections, credits, debits);
		recepient = new Account("Peter", "peter@moi.meme", "pass", new BigDecimal(30.00), 
				new LinkedHashSet<>(), credits, debits);
		transaction = Optional.of(new Transaction(sender, recepient, "22/11/2021", "Cadeau",
				new BigDecimal(10.00)));
	}
	
	@Test
	public void shouldGetTransactionWithExceptionTest() {

		Assertions.assertThrows(TransactionNotFoundException.class, new Executable() {  
	        @Override
			public void execute() throws Throwable {
	        	transactionRepository.findById((long) 1).orElseThrow(() -> 
	        		new TransactionNotFoundException((long) 1));
	        }
	    }, "Could not find transaction with id: 1");
	}
	
	@Test
	public void shouldSaveTransactionTest() {
		when(accountRepository.findByEmail(any())).thenReturn(sender);
		when(accountRepository.findByUserName(any())).thenReturn(recepient);
		when(transactionRepository.save(any())).thenReturn(transaction.get());
		
		TransactionFormDTO form = new TransactionFormDTO();
		Transaction savedTransaction = transactionService.saveTransaction(
					"jhon@moi.meme", form);
		verify(accountService, times(1)).updateSender(any());
		verify(accountService, times(1)).updateRecepient(any());
		Assertions.assertEquals(savedTransaction.getDate(), "22/11/2021");
		Assertions.assertEquals(savedTransaction.getDescription(), "Cadeau");
		Assertions.assertEquals(savedTransaction.getAmount(), new BigDecimal(10.00));
		Assertions.assertEquals(savedTransaction.getSender().getUserName(), "Jhon");
		Assertions.assertEquals(savedTransaction.getRecepient().getUserName(), "Peter");
	}
	
	@Test
	public void shouldFindTransactionsForPageTest() {
		Page<Transaction> transactions = mock(Page.class);
		when(transactionRepository
				.findTransactionsBySenderIdOrRecepientId(any(), any(), any()))
		.thenReturn(transactions);
		
		Assertions.assertEquals(transactionService.findTransactionsForPage(
				mock(Pageable.class), (long) 1), transactions);
	}
	
	@Test
	public void shouldGetConnectionsDTOTest() {		
		Account account1 = new Account();
		Account account2 = new Account();
		Account account3 = new Account();
		connections.add(account1);
		connections.add(account2);
		connections.add(account3);
		ConnectionDTO connection1 = new ConnectionDTO(account1);
		ConnectionDTO connection2 = new ConnectionDTO(account2);
		ConnectionDTO connection3 = new ConnectionDTO(account3);
		List<ConnectionDTO> connectionsList = transactionService.getConnectionsDTO(sender);
		List<ConnectionDTO> connectionsDtoList = Arrays.asList(connection1, connection2, connection3);
		
		Assertions.assertEquals(connectionsList, connectionsDtoList);
	}
	
	@Test
	public void shouldGetTransactionsUserDTOTest() {
		Transaction transaction1 = new Transaction();
		transaction1.setSender(sender);
		transaction1.setRecepient(recepient);
		Transaction transaction2 = new Transaction();
		transaction2.setSender(sender);
		transaction2.setRecepient(recepient);
		Transaction transaction3 = new Transaction();
		transaction3.setSender(sender);
		transaction3.setRecepient(recepient);
		List<Transaction> transactionsList = Arrays.asList(transaction1, transaction2, transaction3);
		Page<Transaction> page = new PageImpl<Transaction>(transactionsList);
		TransactionUserDTO transactionUserDTO1 = new TransactionUserDTO(transaction1);
		TransactionUserDTO transactionUserDTO2 = new TransactionUserDTO(transaction2);
		TransactionUserDTO transactionUserDTO3 = new TransactionUserDTO(transaction3);
		List<TransactionUserDTO> transactionsDtoList = Arrays.asList(
				transactionUserDTO1, transactionUserDTO2, transactionUserDTO3);
		List<TransactionUserDTO> listTransactionsDTO = transactionService.getTransactionsUserDTO(page);
		
		Assertions.assertEquals(transactionsDtoList, listTransactionsDTO);
	}
}
