package com.PayMyBuddy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.dto.AddConnectionDTO;
import com.PayMyBuddy.model.dto.ConnectionDTO;

public interface ConnectionService {

	Account save(Authentication auth, AddConnectionDTO addConnectionDto);

	Page<Account> findConnections(Pageable pageable, Long id);

	boolean isAccountExists(String email);

	boolean isConnectedAccountExists(String connectedEmail, String accountEmail);

	List<ConnectionDTO> getConnectionsDTO(Page<Account> page);
}
