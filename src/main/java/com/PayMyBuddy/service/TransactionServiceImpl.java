package com.PayMyBuddy.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.TransactionDTO;
import com.PayMyBuddy.model.exception.TransactionNotFoundException;
import com.PayMyBuddy.repository.TransactionRepository;
import com.PayMyBuddy.repository.UserRepository;

@Service
public class TransactionServiceImpl implements TransactionService{

	private TransactionRepository transactionRepository;
	
	private UserRepository userRepository;

	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository,
			UserRepository userRepository) {
		super();
		this.transactionRepository = transactionRepository;
		this.userRepository = userRepository;
	}

	public Transaction add(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	public List<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}
	
	public Transaction getTransaction(Long id) {
		return transactionRepository.findById(id).orElseThrow(() -> 
			new TransactionNotFoundException(id));
	}
	
	public Transaction deleteTransaction(Long id) {
		Transaction transaction = getTransaction(id);
		transactionRepository.delete(transaction);
		return transaction;
	}
	
	@Transactional
	public Transaction updateTransaction(Long id, Transaction transaction) {
		Transaction transactionToUpdate = getTransaction(id);
		transactionToUpdate.setSender(transaction.getSender());
		transactionToUpdate.setRecepient(transaction.getRecepient());
		transactionToUpdate.setDate(transaction.getDate());
		transactionToUpdate.setAmount(transaction.getAmount());
		transactionToUpdate.setDescription(transaction.getDescription());
		return transactionToUpdate;
		
	}
	
	public Transaction save(Authentication auth, TransactionDTO form) {
		User sender = userRepository.findByEmail(auth.getName());
		User recepient = userRepository.findByUserName(form.getRecepient().getUserName());
		Transaction transaction = new Transaction();
		transaction.setSender(sender);
		transaction.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		transaction.setDescription("Debit");
		transaction.setAmount(form.getAmount());
		transaction.setRecepient(recepient);
		return transactionRepository.save(transaction);
	}

	public List<Transaction> findTransactionsBySenderId(Long id) {
		return transactionRepository.findTransactionsBySenderId(id);
	}

	
}
