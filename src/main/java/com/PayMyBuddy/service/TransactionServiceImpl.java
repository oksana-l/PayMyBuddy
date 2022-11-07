package com.PayMyBuddy.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
	private UserServiceImpl userService;

	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository,
			UserRepository userRepository, UserServiceImpl userService) {
		super();
		this.transactionRepository = transactionRepository;
		this.userRepository = userRepository;
		this.userService = userService;
	}
	
	public List<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}
	
	public Transaction getTransaction(Long id) {
		return transactionRepository.findById(id).orElseThrow(() -> 
			new TransactionNotFoundException(id));
	}
	
	@Transactional
	public Transaction save(String senderEmail, TransactionDTO form) {
		User sender = userRepository.findByEmail(senderEmail);
		User recepient = userRepository.findByUserName(form.getRecepient().getUserName());
		Transaction transaction = new Transaction();
		transaction.setSender(sender);
		transaction.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		transaction.setAmount(form.getAmount());
		transaction.setRecepient(recepient);
		Transaction saved = transactionRepository.save(transaction);
	    userService.updateSender(saved);
	    userService.updateRecepient(saved);
		return saved;
	}

	public List<Transaction> findTransactionsByUser(Long id) {
		List<Transaction> debitTransactions = transactionRepository.findTransactionsBySenderId(id);
		debitTransactions.stream().forEach(t -> t.setDescription("Debit"));
		List<Transaction> creditTransactions = transactionRepository.findTransactionsByRecepientId(id);
		creditTransactions.stream().forEach(t -> t.setDescription("Credit"));
		List<Transaction> transactions = Stream.concat(debitTransactions.stream(), 
				creditTransactions.stream()).collect(Collectors.toList());
		return transactions;
	}
	
	public Long getId(TransactionDTO form) {
		return transactionRepository.findByRecepientUserName(form.getRecepient().getUserName());
	}


	
}
