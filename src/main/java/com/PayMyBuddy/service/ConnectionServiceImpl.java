package com.PayMyBuddy.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.AddConnectionDTO;
import com.PayMyBuddy.repository.UserRepository;

@Service
public class ConnectionServiceImpl implements ConnectionService{
	
	private UserRepository userRepository;
	
	public ConnectionServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	
	public User save(Authentication auth, AddConnectionDTO addConnectionDto) {
		
		User user = userRepository.findByEmail(auth.getName());
		User connectedUser = userRepository.findByEmail(addConnectionDto.getEmail());
	
		user.getConnections().add(connectedUser);
		return userRepository.save(user);
	}
}
