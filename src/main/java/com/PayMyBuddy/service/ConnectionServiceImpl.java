package com.PayMyBuddy.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.dto.AddConnectionDTO;
import com.PayMyBuddy.repository.AccountRepository;

@Service
public class ConnectionServiceImpl implements ConnectionService{
	
	private AccountRepository accountRepository;

	public ConnectionServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}

	@Override
	public Account save(Authentication auth, AddConnectionDTO addConnectionDto) {
		
		Account account = accountRepository.findByEmail(auth.getName());
		Account connectedAccount = accountRepository.findByEmail(addConnectionDto.getEmail());
		account.getConnections().add(connectedAccount);
		
		return accountRepository.save(account);
	}
	
	@Override
	public boolean isUserExist(String email) {
		
		return  accountRepository.findByEmail(email) != null;
	}
}
