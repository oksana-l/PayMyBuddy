package com.PayMyBuddy.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	private UserService userService;

	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository,
			UserRepository userRepository, UserService userService) {
		super();
		this.transactionRepository = transactionRepository;
		this.userRepository = userRepository;
		this.userService = userService;
	}
	
	@Override
	public Transaction getTransaction(Long id) {
		return transactionRepository.findById(id).orElseThrow(() -> 
			new TransactionNotFoundException(id));
	}
	
	@Override
	@Transactional
	public Transaction saveTransaction(String senderEmail, TransactionFormDTO form) {
		
		User sender = userRepository.findByEmail(senderEmail);
		User recepient = userRepository.findByUserName(form.getRecepientUserName());
		Transaction transaction = new Transaction();
		Transaction saved = new Transaction();
		transaction.setSender(sender);
		transaction.setDescription(form.getDescription());
		transaction.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		transaction.setAmount(form.getAmount());
		transaction.setRecepient(recepient);
		saved = transactionRepository.save(transaction);
	    userService.updateSender(saved);
	    userService.updateRecepient(saved);

		return saved;
	}
	
	@Override
	public Page<Transaction> findTransactionWithSorting(Pageable pageable, Long id) {
	    
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
