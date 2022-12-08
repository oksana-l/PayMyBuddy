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
import org.mockito.AdditionalAnswers;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.dto.UserDTO;
import com.PayMyBuddy.model.exception.AccountExistsException;
import com.PayMyBuddy.repository.UserRepository;
import com.PayMyBuddy.service.UserService;
import com.PayMyBuddy.service.UserServiceImpl;

public class UserServiceImplTest {

	private UserRepository userRepository;
	private UserService userService;
	private Optional<Account> account;
	private Account connectedAccount;
	private Transaction transaction;
	private List<Transaction> credits  = new ArrayList<Transaction>();
	private List<Transaction> debits = new ArrayList<Transaction>();
	
	@BeforeEach
	public void setUp() {
		userRepository = mock(UserRepository.class);
		userService = new UserServiceImpl(userRepository);
		account = Optional.of(new Account("Jhon", "jhon@moi.meme", "pass", new BigDecimal(0.00), 
				new LinkedHashSet<>(), credits, debits));
		connectedAccount = new Account("Peter", "peter@moi.meme", "pass", new BigDecimal(30.00), 
				new LinkedHashSet<>(), credits, debits);
		transaction = new Transaction(connectedAccount, account.get(), "22/11/2021", "Cadeau",
				new BigDecimal(10.00));
		debits.add(transaction);
	}
	
	@Test
	public void shouldGetUserTest() {
		when(userRepository.findById(any())).thenReturn(account);
		
		Assertions.assertEquals(userService.getAccount((long) 1).getUserName(), account
				.get().getUserName());
	}
	
	@Test
	public void shouldFindUserByUserNameTest() {
		when(userRepository.findByUserName(any())).thenReturn(account.get());
		
		Assertions.assertEquals(userService.findAccountByUserName("Jhon"), account.get());
	}
	
	@Test
	public void shouldFindUserByUserNameIfUserIsNull() {
		when(userRepository.findByUserName(null)).thenThrow(new UsernameNotFoundException(
				"No account found with userName : " + account.get().getUserName()));
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> userService
				.findAccountByUserName(account.get().getUserName()));
	}
	
	@Test
	public void shouldFindUserByEmail() {
		when(userRepository.findByEmail(any())).thenReturn(account.get());
		
		Assertions.assertEquals(userService.findAccountByEmail("jhon@moi.meme"), account.get());
	}
	
	@Test
	public void shouldFindUserByEmailIfUserIsNull() {
		when(userRepository.findByEmail(null)).thenThrow(new UsernameNotFoundException(
				"No account found with email : " + account.get().getEmail()));
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> userService
				.findAccountByEmail(account.get().getEmail()));
	}
	
	@Test
	public void shouldUpdateUserTest() {
		when(userRepository.findById(any())).thenReturn(account);
		
		Account accountForUpdate = new Account("Peter", "peter@moi.meme", "password", new BigDecimal(30.00), 
				new LinkedHashSet<>(), null, null);
		accountForUpdate.getConnections().add(connectedAccount);
		Account updatedAccount = userService.updateAccount((long) 1, accountForUpdate);
		
		Assertions.assertEquals(updatedAccount.getUserName(), accountForUpdate.getUserName());
		Assertions.assertEquals(updatedAccount.getEmail(), accountForUpdate.getEmail());
		Assertions.assertEquals(updatedAccount.getPassword(), accountForUpdate.getPassword());
		Assertions.assertEquals(updatedAccount.getBalance(), accountForUpdate.getBalance());
		Assertions.assertEquals(updatedAccount.getConnections(), accountForUpdate.getConnections());
		Assertions.assertEquals(updatedAccount.getCredits(), accountForUpdate.getCredits());
		Assertions.assertEquals(updatedAccount.getDebits(), accountForUpdate.getDebits());
	}
	
	@Test
	public void shouldSaveTest() throws AccountExistsException {
		when(userRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
		
		account.get().setCredits(credits);
		UserDTO userDto = new UserDTO(account.get());
		Account savedAccount = userService.save(userDto);
		
		Assertions.assertEquals(savedAccount.getUserName(), account.get().getUserName());
		Assertions.assertEquals(savedAccount.getEmail(), account.get().getEmail());
		Assertions.assertEquals(savedAccount.getPassword(), account.get().getPassword());
		Assertions.assertEquals(savedAccount.getBalance(), account.get().getBalance());
		Assertions.assertEquals(savedAccount.getConnections(), account.get().getConnections());
		Assertions.assertEquals(savedAccount.getDebits(), account.get().getDebits());
		Assertions.assertEquals(savedAccount.getCredits(), account.get().getCredits());
	}
	
	@Test
	public void shouldIfUserExistTest() {
		when(userRepository.findByUserName("Peter")).thenReturn(null);
		when(userRepository.findByUserName("Jhon")).thenReturn(account.get());
		when(userRepository.findByEmail("peter@moi.meme")).thenReturn(null);
		when(userRepository.findByEmail("jhon@moi.meme")).thenReturn(account.get());
		
		UserDTO userDto1 = new UserDTO(account.get());
		UserDTO userDto2 = new UserDTO(connectedAccount);
		
		Assertions.assertTrue(userService.ifUserExist(userDto1));
		Assertions.assertFalse(userService.ifUserExist(userDto2));
	}
	
	@Test
	public void shouldUpdateSenderTest() {
		userService.updateSender(transaction);
		
		verify(userRepository,times(1)).save(connectedAccount);
		Assertions.assertEquals(connectedAccount.getBalance(), new BigDecimal(20.00));
	}
	
	@Test
	public void shouldUpdateRecepientTest() {
		userService.updateRecepient(transaction);
		
		verify(userRepository,times(1)).save(account.get());
		Assertions.assertEquals(account.get().getBalance(), new BigDecimal(10.00));
		
	}
	
	@Test
	public void shouldUserHasAmountTest() {
		when(userRepository.findByEmail(any())).thenReturn(connectedAccount);
		
		Assertions.assertTrue(userService.userHasAmount(connectedAccount.getEmail(), new BigDecimal(30.00)));
	}
}