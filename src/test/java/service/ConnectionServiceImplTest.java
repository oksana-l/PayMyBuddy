package service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.AddConnectionDTO;
import com.PayMyBuddy.repository.UserRepository;
import com.PayMyBuddy.service.ConnectionServiceImpl;

public class ConnectionServiceImplTest {

	ConnectionServiceImpl connectionService;
	UserRepository userRepository;
	Authentication auth;
	AddConnectionDTO addConnectionDto;
	User user;
	User connectedUser;
	
	@BeforeEach
	public void setUp() {
		userRepository = mock(UserRepository.class);
		connectionService = new ConnectionServiceImpl(userRepository);
		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		auth = new UsernamePasswordAuthenticationToken ("Jhon", "pass", authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
		addConnectionDto = new AddConnectionDTO();
		addConnectionDto.setEmail("peter@moi.meme");
		user = new User("Jhon", "jhon@moi.meme", "pass", new BigDecimal(0.00), 
				new LinkedHashSet<>(), Arrays.asList(), Arrays.asList());
		connectedUser = new User("Peter", "peter@moi.meme", "pass",
				new BigDecimal(0.00), new LinkedHashSet<>(), 
				Arrays.asList(), Arrays.asList());
	}
	
	@Test
	public void shouldSaveTest() {
		
		when(userRepository.findByEmail(auth.getName())).thenReturn(user);
		when(userRepository.findByEmail(addConnectionDto.getEmail())).thenReturn(connectedUser);
		when(userRepository.save(user)).thenReturn(user);
		
		Assertions.assertFalse(connectionService.save(auth, addConnectionDto).getConnections().isEmpty());
		Assertions.assertEquals(connectionService.save(auth, addConnectionDto).getUserName(), "Jhon");
		Assertions.assertEquals(connectionService.save(auth, addConnectionDto).getConnections()
				.stream().findFirst().get().getUserName(), "Peter");
	}
	
	@Test
	public void shouldIfUserExisteTest() {

		when(userRepository.findByEmail(addConnectionDto.getEmail())).thenReturn(connectedUser);
		
		Assertions.assertTrue(connectionService.ifUserExist(addConnectionDto));
	}
}
