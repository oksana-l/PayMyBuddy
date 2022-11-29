package service;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.User;
import com.PayMyBuddy.repository.TransactionRepository;
import com.PayMyBuddy.repository.UserRepository;
import com.PayMyBuddy.service.TransactionServiceImpl;
import com.PayMyBuddy.service.UserServiceImpl;

public class TransactionServiceImplTest {

	TransactionRepository transactionRepository;
	UserRepository userRepository;
	UserServiceImpl userService;
	TransactionServiceImpl transactionService;
	User sender;
	User recepient;
	Transaction transaction;
	List<Transaction> credits  = new ArrayList<Transaction>();
	List<Transaction> debits = new ArrayList<Transaction>();
	
	@BeforeEach
	public void setUp() {
		transactionRepository = mock(TransactionRepository.class);
		userRepository = mock(UserRepository.class);
		userService = mock(UserServiceImpl.class);
		transactionService = new TransactionServiceImpl(
				transactionRepository, userRepository, userService);
	}
	
}
