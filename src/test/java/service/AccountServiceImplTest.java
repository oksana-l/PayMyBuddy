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
import com.PayMyBuddy.model.dto.AccountDTO;
import com.PayMyBuddy.model.exception.AccountExistsException;
import com.PayMyBuddy.repository.AccountRepository;
import com.PayMyBuddy.service.AccountService;
import com.PayMyBuddy.service.AccountServiceImpl;

public class AccountServiceImplTest {

	private AccountRepository accountRepository;
	private AccountService accountService;
	private Optional<Account> account;
	private Account connectedAccount;
	private Transaction transaction;
	private List<Transaction> credits  = new ArrayList<Transaction>();
	private List<Transaction> debits = new ArrayList<Transaction>();
	
	@BeforeEach
	public void setUp() {
		accountRepository = mock(AccountRepository.class);
		accountService = new AccountServiceImpl(accountRepository);
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
		when(accountRepository.findById(any())).thenReturn(account);
		
		Assertions.assertEquals(accountService.getAccount((long) 1).getUserName(), account
				.get().getUserName());
	}
	
	@Test
	public void shouldFindUserByUserNameTest() {
		when(accountRepository.findByUserName(any())).thenReturn(account.get());
		
		Assertions.assertEquals(accountService.findAccountByUserName("Jhon"), account.get());
	}
	
	@Test
	public void shouldFindUserByUserNameIfUserIsNull() {
		when(accountRepository.findByUserName(null)).thenThrow(new UsernameNotFoundException(
				"No account found with userName : " + account.get().getUserName()));
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> accountService
				.findAccountByUserName(account.get().getUserName()));
	}
	
	@Test
	public void shouldFindUserByEmail() {
		when(accountRepository.findByEmail(any())).thenReturn(account.get());
		
		Assertions.assertEquals(accountService.findAccountByEmail("jhon@moi.meme"), account.get());
	}
	
	@Test
	public void shouldFindUserByEmailIfUserIsNull() {
		when(accountRepository.findByEmail(null)).thenThrow(new UsernameNotFoundException(
				"No account found with email : " + account.get().getEmail()));
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> accountService
				.findAccountByEmail(account.get().getEmail()));
	}
	
	@Test
	public void shouldUpdateUserTest() {
		when(accountRepository.findById(any())).thenReturn(account);
		
		Account accountForUpdate = new Account("Peter", "peter@moi.meme", "password", new BigDecimal(30.00), 
				new LinkedHashSet<>(), null, null);
		accountForUpdate.getConnections().add(connectedAccount);
		Account updatedAccount = accountService.updateAccount((long) 1, accountForUpdate);
		
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
		when(accountRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
		
		account.get().setCredits(credits);
		AccountDTO accountDto = new AccountDTO(account.get());
		Account savedAccount = accountService.save(accountDto);
		
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
		when(accountRepository.findByUserName("Peter")).thenReturn(null);
		when(accountRepository.findByUserName("Jhon")).thenReturn(account.get());
		when(accountRepository.findByEmail("peter@moi.meme")).thenReturn(null);
		when(accountRepository.findByEmail("jhon@moi.meme")).thenReturn(account.get());
		
		AccountDTO userDto1 = new AccountDTO(account.get());
		AccountDTO userDto2 = new AccountDTO(connectedAccount);
		
		Assertions.assertTrue(accountService.ifUserExist(userDto1));
		Assertions.assertFalse(accountService.ifUserExist(userDto2));
	}
	
	@Test
	public void shouldUpdateSenderTest() {
		accountService.updateSender(transaction);
		
		verify(accountRepository,times(1)).save(connectedAccount);
		Assertions.assertEquals(connectedAccount.getBalance(), new BigDecimal(20.00));
	}
	
	@Test
	public void shouldUpdateRecepientTest() {
		accountService.updateRecepient(transaction);
		
		verify(accountRepository,times(1)).save(account.get());
		Assertions.assertEquals(account.get().getBalance(), new BigDecimal(10.00));
		
	}
	
	@Test
	public void shouldUserHasAmountTest() {
		when(accountRepository.findByEmail(any())).thenReturn(connectedAccount);
		
		Assertions.assertTrue(accountService.userHasAmount(connectedAccount.getEmail(), new BigDecimal(30.00)));
	}
}