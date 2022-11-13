package com.PayMyBuddy.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
		Transaction saved = new Transaction();
		transaction.setSender(sender);
		transaction.setDescription(form.getDescription());
		transaction.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		transaction.setAmount(form.getAmount());
		transaction.setRecepient(recepient);
		BigDecimal balance = transaction.getSender().getBalance();
		BigDecimal amount = form.getAmount();
		if (balance.compareTo(amount) >= 0) {
			saved = transactionRepository.save(transaction);
	    userService.updateSender(saved);
	    userService.updateRecepient(saved);
		} // else : declencher une erreur
		return saved;
	}

	public List<Transaction> findTransactionsByUser(Long id) {
		List<Transaction> transactions = transactionRepository
				.findTransactionsBySenderIdOrRecepientId(id, id);
		transactions.forEach(t -> {
			if (t.getSender().getId().equals(id)) {
				t.setAmount(t.getAmount().negate());
			} 
		});
		return transactions;
	}
	
	public Long getId(TransactionFormDTO form) {
		return transactionRepository.findByRecepientUserName(form.getRecepientUserName());
	}
	
	public Page<Transaction> findPage(int pageNumber, Long id) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		Page<Transaction> transactions = transactionRepository
				.findTransactionsBySenderIdOrRecepientId(id, id, pageable);
		transactions.forEach(t -> {
			if (t.getSender().getId().equals(id)) {
				t.setAmount(t.getAmount().negate());
			} 
		});
		return transactions;
	}
	
}
