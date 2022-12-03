package com.PayMyBuddy.service;

import org.springframework.security.core.Authentication;

import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.AddConnectionDTO;

public interface ConnectionService {

	User save(Authentication auth, AddConnectionDTO addConnectionDto);

	boolean isUserExist(String email);

}
