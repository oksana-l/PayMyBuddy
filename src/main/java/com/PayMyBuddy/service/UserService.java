package com.PayMyBuddy.service;

import java.math.BigDecimal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.dto.UserDTO;
import com.PayMyBuddy.model.exception.AccountExistsException;

public interface UserService extends UserDetailsService{

	UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

	Account findAccountByUserName(String userName);

	Account findAccountByEmail(String email);

	Account updateAccount(Long id, Account account);

	void updateSender(Transaction transaction);

	void updateRecepient(Transaction transaction);

	boolean userHasAmount(String senderEmail, BigDecimal amount);

	Account save(UserDTO registrationDto) throws AccountExistsException;

	Account getAccount(Long id);

	boolean ifUserExist(UserDTO userDto);
}
