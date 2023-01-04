package com.PayMyBuddy.service;

import java.math.BigDecimal;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.dto.AccountDTO;
import com.PayMyBuddy.model.exception.AccountExistsException;

public interface AccountService extends UserDetailsService{

	Account findAccountByUserName(String userName);

	Account findAccountByEmail(String email);

	Account updateAccount(Long id, Account account);

	void updateSender(Transaction transaction);

	void updateRecepient(Transaction transaction);

	boolean userHasAmount(String senderEmail, BigDecimal amount);

	Account save(AccountDTO registrationDto) throws AccountExistsException;

	Account getAccount(Long id);

	boolean ifUserExist(AccountDTO accountDto);
}
