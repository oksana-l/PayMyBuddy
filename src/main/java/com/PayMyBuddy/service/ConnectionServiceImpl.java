package com.PayMyBuddy.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.dto.AddConnectionDTO;
import com.PayMyBuddy.model.dto.ConnectionDTO;
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
	public Page<Account> findConnections(Pageable pageable, Long id) {
		
		return accountRepository.findAccountsByConnectedAccountId(id, pageable);
	}

	@Override
	public boolean isAccountExists(String email) {
		
		return  accountRepository.findByEmail(email) != null;
	}

	@Override
	public boolean isConnectedAccountExists(String connectedEmail, String accountEmail) {
		Set<Account> connectedAccounts = accountRepository.findByEmail(accountEmail)
				.getConnections();
		if (connectedAccounts.isEmpty()) {
			return false;
		}
		else {
			return connectedAccounts.stream().map(Account::getEmail).anyMatch(connectedEmail::equalsIgnoreCase);
		}
	}

	@Override
	public List<ConnectionDTO> getConnectionsDTO(Page<Account> page) {
		
		return page.getContent().stream()
				.map(u -> new ConnectionDTO(u)).collect(Collectors.toList());
	}
}
