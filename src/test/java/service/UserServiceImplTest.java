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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.UserDTO;
import com.PayMyBuddy.model.exception.UserExistsException;
import com.PayMyBuddy.repository.UserRepository;
import com.PayMyBuddy.service.UserServiceImpl;

public class UserServiceImplTest {

	UserRepository userRepository;
	UserServiceImpl userService;
	Optional<User> user;
	User connectedUser;
	Transaction transaction;
	List<Transaction> credits  = new ArrayList<Transaction>();
	List<Transaction> debits = new ArrayList<Transaction>();
	
	@BeforeEach
	public void setUp() {
		userRepository = mock(UserRepository.class);
		userService = new UserServiceImpl(userRepository);
		user = Optional.ofNullable(new User("Jhon", "jhon@moi.meme", "pass", 
				new BigDecimal(0.00), new LinkedHashSet<>(), 
				credits, debits));
		connectedUser = new User("Peter", "peter@moi.meme", "pass", new BigDecimal(30.00), 
				new LinkedHashSet<>(), credits, debits);
		transaction = new Transaction(connectedUser, user.get(), "22/11/2021", "Cadeau",
				new BigDecimal(10.00));
		debits.add(transaction);
	}
	
	@Test
	public void shouldAddUserTest() {
		when(userRepository.save(user.get())).thenReturn(user.get());
		
		Assertions.assertEquals(userService.addUser(user.get()).getUserName(), "Jhon");
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
	public void shouldUpdateUser() {
		User updatedUser = new User("Peter", "peter@moi.meme", "pass", new BigDecimal(30.00), 
				new LinkedHashSet<>(), Arrays.asList(), Arrays.asList());
		updatedUser.getConnections().add(connectedUser);
		
		when(userRepository.findById(any())).thenReturn(user);
		
		Assertions.assertEquals(userService.updateUser((long) 1, updatedUser).getUserName(),
				updatedUser.getUserName());
	}
	
	@Test //??????
	public void shouldSaveTest() throws UserExistsException {
		when(userRepository.save(any())).thenReturn(user.get());
		UserDTO userDto = new UserDTO(user.get());
		Assertions.assertEquals(userService.save(userDto).getEmail(), user.get().getEmail());
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
	}
	
	@Test
	public void shouldUpdateRecepientTest() {
		userService.updateRecepient(transaction);
		verify(userRepository,times(1)).save(user.get());
	}
	
	@Test
	public void shouldUserHasAmountTest() {
		when(userRepository.findByEmail(any())).thenReturn(connectedUser);
		
		Assertions.assertTrue(userService.userHasAmount(connectedUser.getEmail(), new BigDecimal(30.00)));
	}
}