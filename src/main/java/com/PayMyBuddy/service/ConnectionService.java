package com.PayMyBuddy.service;

import org.springframework.security.core.Authentication;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.dto.AddConnectionDTO;

public interface ConnectionService {

	Account save(Authentication auth, AddConnectionDTO addConnectionDto);

	boolean isUserExist(String email);

}
