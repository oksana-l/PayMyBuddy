package service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.data.domain.Page;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.TransactionFormDTO;
import com.PayMyBuddy.model.exception.TransactionNotFoundException;
import com.PayMyBuddy.repository.TransactionRepository;
import com.PayMyBuddy.repository.UserRepository;
import com.PayMyBuddy.service.TransactionService;
import com.PayMyBuddy.service.TransactionServiceImpl;
import com.PayMyBuddy.service.UserService;
import com.PayMyBuddy.service.UserServiceImpl;

public class TransactionServiceImplTest {

	private TransactionRepository transactionRepository;
	private UserRepository userRepository;
	private UserService userService;
	private TransactionService transactionService;
	private User sender;
	private User recepient;
	private Optional<Transaction> transaction;
	private List<Transaction> credits  = new ArrayList<Transaction>();
	private List<Transaction> debits = new ArrayList<Transaction>();
	
	@BeforeEach
	public void setUp() {
		transactionRepository = mock(TransactionRepository.class);
		userRepository = mock(UserRepository.class);
		userService = mock(UserServiceImpl.class);
		transactionService = new TransactionServiceImpl(
				transactionRepository, userRepository, userService);
		sender = new User("Jhon", "jhon@moi.meme", "pass", new BigDecimal(0.00), 
				new LinkedHashSet<>(), credits, debits);
		recepient = new User("Peter", "peter@moi.meme", "pass", new BigDecimal(30.00), 
				new LinkedHashSet<>(), credits, debits);
		transaction = Optional.of(new Transaction(sender, recepient, "22/11/2021", "Cadeau",
				new BigDecimal(10.00)));
	}
	
	@Test
	public void shouldGetTransactionTest() {
		when(transactionRepository.findById(any())).thenReturn(transaction);
		Assertions.assertNotNull(transactionService.getTransaction((long) 1));
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
		when(userRepository.findByEmail(any())).thenReturn(sender);
		when(userRepository.findByUserName(any())).thenReturn(recepient);
		when(transactionRepository.save(any())).thenReturn(transaction.get());
		
		TransactionFormDTO form = new TransactionFormDTO();
		Transaction savedTransaction = transactionService.saveTransaction(
					"jhon@moi.meme", form);
		verify(userService, times(1)).updateSender(any());
		verify(userService, times(1)).updateRecepient(any());
		Assertions.assertEquals(savedTransaction.getDate(), "22/11/2021");
		Assertions.assertEquals(savedTransaction.getDescription(), "Cadeau");
		Assertions.assertEquals(savedTransaction.getAmount(), new BigDecimal(10.00));
		Assertions.assertEquals(savedTransaction.getSender().getUserName(), "Jhon");
		Assertions.assertEquals(savedTransaction.getRecepient().getUserName(), "Peter");
		
		
	}
	
	@Test
	public void shouldFindTransactionsWithSortingTest() {
		Page<Transaction> transactions = mock(Page.class);
		when(transactionRepository
				.findTransactionsBySenderIdOrRecepientId(any(), any(), any()))
		.thenReturn(transactions);
		
		Assertions.assertEquals(transactionRepository
				.findTransactionsBySenderIdOrRecepientId(null, null, null).getSize(), 0);
	}
	
}
