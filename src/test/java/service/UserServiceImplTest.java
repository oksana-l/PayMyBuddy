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

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.UserDTO;
import com.PayMyBuddy.model.exception.UserExistsException;
import com.PayMyBuddy.repository.UserRepository;
import com.PayMyBuddy.service.UserService;
import com.PayMyBuddy.service.UserServiceImpl;

public class UserServiceImplTest {

	private UserRepository userRepository;
	private UserService userService;
	private Optional<User> user;
	private User connectedUser;
	private Transaction transaction;
	private List<Transaction> credits  = new ArrayList<Transaction>();
	private List<Transaction> debits = new ArrayList<Transaction>();
	
	@BeforeEach
	public void setUp() {
		userRepository = mock(UserRepository.class);
		userService = new UserServiceImpl(userRepository);
		user = Optional.of(new User("Jhon", "jhon@moi.meme", "pass", new BigDecimal(0.00), 
				new LinkedHashSet<>(), credits, debits));
		connectedUser = new User("Peter", "peter@moi.meme", "pass", new BigDecimal(30.00), 
				new LinkedHashSet<>(), credits, debits);
		transaction = new Transaction(connectedUser, user.get(), "22/11/2021", "Cadeau",
				new BigDecimal(10.00));
		debits.add(transaction);
	}
	
	@Test
	public void shouldGetUserTest() {
		when(userRepository.findById(any())).thenReturn(user);
		
		Assertions.assertEquals(userService.getUser((long) 1).getUserName(), user
				.get().getUserName());
	}
	
	@Test
	public void shouldFindUserByUserNameTest() {
		when(userRepository.findByUserName(any())).thenReturn(user.get());
		
		Assertions.assertEquals(userService.findUserByUserName("Jhon"), user.get());
	}
	
	@Test
	public void shouldFindUserByUserNameIfUserIsNull() {
		when(userRepository.findByUserName(null)).thenThrow(new UsernameNotFoundException(
				"No user found with userName : " + user.get().getUserName()));
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> userService
				.findUserByUserName(user.get().getUserName()));
	}
	
	@Test
	public void shouldFindUserByEmail() {
		when(userRepository.findByEmail(any())).thenReturn(user.get());
		
		Assertions.assertEquals(userService.findUserByEmail("jhon@moi.meme"), user.get());
	}
	
	@Test
	public void shouldFindUserByEmailIfUserIsNull() {
		when(userRepository.findByEmail(null)).thenThrow(new UsernameNotFoundException(
				"No user found with email : " + user.get().getEmail()));
		
		Assertions.assertThrows(UsernameNotFoundException.class, () -> userService
				.findUserByEmail(user.get().getEmail()));
	}
	
	@Test
	public void shouldUpdateUserTest() {
		when(userRepository.findById(any())).thenReturn(user);
		
		User userForUpdate = new User("Peter", "peter@moi.meme", "password", new BigDecimal(30.00), 
				new LinkedHashSet<>(), null, null);
		userForUpdate.getConnections().add(connectedUser);
		User updatedUser = userService.updateUser((long) 1, userForUpdate);
		
		Assertions.assertEquals(updatedUser.getUserName(), userForUpdate.getUserName());
		Assertions.assertEquals(updatedUser.getEmail(), userForUpdate.getEmail());
		Assertions.assertEquals(updatedUser.getPassword(), userForUpdate.getPassword());
		Assertions.assertEquals(updatedUser.getBalance(), userForUpdate.getBalance());
		Assertions.assertEquals(updatedUser.getConnections(), userForUpdate.getConnections());
		Assertions.assertEquals(updatedUser.getCredits(), userForUpdate.getCredits());
		Assertions.assertEquals(updatedUser.getDebits(), userForUpdate.getDebits());
	}
	
	@Test
	public void shouldSaveTest() throws UserExistsException {
		when(userRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
		
		user.get().setCredits(credits);
		UserDTO userDto = new UserDTO(user.get());
		User savedUser = userService.save(userDto);
		
		Assertions.assertEquals(savedUser.getUserName(), user.get().getUserName());
		Assertions.assertEquals(savedUser.getEmail(), user.get().getEmail());
		Assertions.assertEquals(savedUser.getPassword(), user.get().getPassword());
		Assertions.assertEquals(savedUser.getBalance(), user.get().getBalance());
		Assertions.assertEquals(savedUser.getConnections(), user.get().getConnections());
		Assertions.assertEquals(savedUser.getDebits(), user.get().getDebits());
		Assertions.assertEquals(savedUser.getCredits(), user.get().getCredits());
	}
	
	@Test
	public void shouldIfUserExistTest() {
		when(userRepository.findByUserName("Peter")).thenReturn(null);
		when(userRepository.findByUserName("Jhon")).thenReturn(user.get());
		when(userRepository.findByEmail("peter@moi.meme")).thenReturn(null);
		when(userRepository.findByEmail("jhon@moi.meme")).thenReturn(user.get());
		
		UserDTO userDto1 = new UserDTO(user.get());
		UserDTO userDto2 = new UserDTO(connectedUser);
		
		Assertions.assertTrue(userService.ifUserExist(userDto1));
		Assertions.assertFalse(userService.ifUserExist(userDto2));
	}
	
	@Test
	public void shouldUpdateSenderTest() {
		userService.updateSender(transaction);
		
		verify(userRepository,times(1)).save(connectedUser);
		Assertions.assertEquals(connectedUser.getBalance(), new BigDecimal(20.00));
	}
	
	@Test
	public void shouldUpdateRecepientTest() {
		userService.updateRecepient(transaction);
		
		verify(userRepository,times(1)).save(user.get());
		Assertions.assertEquals(user.get().getBalance(), new BigDecimal(10.00));
		
	}
	
	@Test
	public void shouldUserHasAmountTest() {
		when(userRepository.findByEmail(any())).thenReturn(connectedUser);
		
		Assertions.assertTrue(userService.userHasAmount(connectedUser.getEmail(), new BigDecimal(30.00)));
	}
}