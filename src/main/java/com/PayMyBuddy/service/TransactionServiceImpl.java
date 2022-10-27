package com.PayMyBuddy.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.User;
import com.PayMyBuddy.repository.TransactionRepository;
import com.PayMyBuddy.repository.UserRepository;
import com.PayMyBuddy.web.dto.TransactionDTO;

@Service
public class TransactionServiceImpl implements TransactionService{

	private TransactionRepository transactionRepository;
	
	private UserRepository userRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {
		super();
		this.transactionRepository = transactionRepository;
		this.userRepository = userRepository;
	}

	public Transaction save(Authentication auth, TransactionDTO form) {
		User sender = userRepository.findByEmail(auth.getName());
		User recepient = userRepository.findByUserName(form.getConnectedUserName());
		Transaction transaction = new Transaction();
		transaction.setSender(sender);
		transaction.setDate(form.getDate());
		transaction.setAmount(form.getAmount());
		transaction.setRecepient(recepient);
		return transactionRepository.save(transaction);
	}

	public List <Transaction> findTransactionsBySenderId(Long id) {
		
		return transactionRepository.findTransactionsBySenderId(id);
	}

	
}
