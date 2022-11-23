package service;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.PayMyBuddy.repository.UserRepository;

public class ConnectionServiceImplTest {

	UserRepository userRepository;
	
	@BeforeEach
	public void setUp() {
		userRepository = mock(UserRepository.class);
	}
	
	@Test
	public void shouldSaveTest() {
		
	}
}
