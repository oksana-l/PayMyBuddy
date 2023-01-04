package com.PayMyBuddy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.dto.AddConnectionDTO;

public interface ConnectionService {

	Account save(Authentication auth, AddConnectionDTO addConnectionDto);

	Page<Account> findConnections(Pageable pageable, Long id);

	boolean isAccountExist(String email);

	boolean isConnectedAccountExist(String connectedEmail, String accountEmail);
}
