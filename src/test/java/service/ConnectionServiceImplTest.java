package service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.dto.AddConnectionDTO;
import com.PayMyBuddy.repository.AccountRepository;
import com.PayMyBuddy.service.ConnectionServiceImpl;

public class ConnectionServiceImplTest {

	private ConnectionServiceImpl connectionService;
	private AccountRepository accountRepository;
	private AddConnectionDTO addConnectionDto;
	private static final String AUTH_MAIL = "jhon@moi.meme";
	
	@BeforeEach
	public void setUp() {
		accountRepository = mock(AccountRepository.class);
		connectionService = new ConnectionServiceImpl(accountRepository);
		addConnectionDto = new AddConnectionDTO();
		addConnectionDto.setEmail("peter@moi.meme");
	}
	
	@Test
	public void shouldSaveTest() {
	
		Authentication auth = new UsernamePasswordAuthenticationToken (AUTH_MAIL, null, null);
		
		when(accountRepository.findByEmail(AUTH_MAIL)).thenReturn(new Account("Jhon", AUTH_MAIL, "pass",
				new BigDecimal(0.00), new LinkedHashSet<>(), Arrays.asList(), Arrays.asList()));
		when(accountRepository.findByEmail("peter@moi.meme")).thenReturn(new Account("Peter", "peter@moi.meme", "pass",
				new BigDecimal(0.00), new LinkedHashSet<>(), Arrays.asList(), Arrays.asList()));
		when(accountRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
		
		Account usersaved = connectionService.save(auth, addConnectionDto);
		
		Assertions.assertFalse(usersaved.getConnections().isEmpty());
		Assertions.assertEquals(usersaved.getUserName(), "Jhon");
		Assertions.assertEquals(usersaved.getConnections()
				.stream().findFirst().get().getUserName(), "Peter");
	}
	
	@Test
	public void shouldIfUserExisteTest() {

		when(accountRepository.findByEmail(AUTH_MAIL)).thenReturn(new Account());
		
		Assertions.assertTrue(connectionService.isAccountExist(AUTH_MAIL));
	}
	
	@Test
	public void shouldIfUserNotExisteTest() {
		
		Assertions.assertFalse(connectionService.isAccountExist(AUTH_MAIL));
	}
}
