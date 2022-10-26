package com.PayMyBuddy.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.PayMyBuddy.model.User;
import com.PayMyBuddy.web.dto.UserDTO;
import com.PayMyBuddy.web.dto.UserExistsException;

public interface UserService extends UserDetailsService{
	User save(UserDTO registrationDto) throws UserExistsException;

	UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;
}
