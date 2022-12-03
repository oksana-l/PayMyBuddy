package com.PayMyBuddy.service;

import java.math.BigDecimal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.UserDTO;
import com.PayMyBuddy.model.exception.UserExistsException;

public interface UserService extends UserDetailsService{

	UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

	User findUserByUserName(String userName);

	User findUserByEmail(String email);

	User updateUser(Long id, User user);

	void updateSender(Transaction transaction);

	void updateRecepient(Transaction transaction);

	boolean userHasAmount(String senderEmail, BigDecimal amount);

	User save(UserDTO registrationDto) throws UserExistsException;

	User getUser(Long id);

	boolean ifUserExist(UserDTO userDto);
}
