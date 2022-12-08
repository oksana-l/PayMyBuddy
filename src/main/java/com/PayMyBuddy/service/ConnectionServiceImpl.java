package com.PayMyBuddy.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.dto.AddConnectionDTO;
import com.PayMyBuddy.repository.UserRepository;

@Service
public class ConnectionServiceImpl implements ConnectionService{
	
	private UserRepository userRepository;

	public ConnectionServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public Account save(Authentication auth, AddConnectionDTO addConnectionDto) {
		
		Account account = userRepository.findByEmail(auth.getName());
		Account connectedAccount = userRepository.findByEmail(addConnectionDto.getEmail());
		account.getConnections().add(connectedAccount);
		
		return userRepository.save(account);
	}
	
	@Override
	public boolean isUserExist(String email) {
		
		return  userRepository.findByEmail(email) != null;
	}
}
