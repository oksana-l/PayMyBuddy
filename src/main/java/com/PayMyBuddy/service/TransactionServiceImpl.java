package com.PayMyBuddy.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.TransactionFormDTO;
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
	public Transaction save(String senderEmail, TransactionFormDTO form) {
		User sender = userRepository.findByEmail(senderEmail);
		User recepient = userRepository.findByUserName(form.getRecepientUserName());
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
		List<Transaction> transactions = transactionRepository.findTransactionsBySenderIdOrRecepientId(id, id);
		transactions.forEach(t -> {
			if (t.getSender().getId().equals(id)) {
				t.setDescription("Debit");
			} else {
				t.setDescription("Credit");
			}
		});
		return transactions;
	}
	
	public Long getId(TransactionFormDTO form) {
		return transactionRepository.findByRecepientUserName(form.getRecepientUserName());
	}


	
}
