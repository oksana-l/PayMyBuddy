package com.PayMyBuddy.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.dto.ConnectionDTO;
import com.PayMyBuddy.model.dto.TransactionFormDTO;
import com.PayMyBuddy.model.dto.TransactionUserDTO;
import com.PayMyBuddy.repository.AccountRepository;
import com.PayMyBuddy.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService{

	private TransactionRepository transactionRepository;
	private AccountRepository accountRepository;
	private AccountService accountService;

	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository,
			AccountRepository accountRepository, AccountService accountService) {
		super();
		this.transactionRepository = transactionRepository;
		this.accountRepository = accountRepository;
		this.accountService = accountService;
	}
	
	@Override
	@Transactional
	public Transaction saveTransaction(String senderEmail, TransactionFormDTO form) {
		
		Account sender = accountRepository.findByEmail(senderEmail);
		Account recepient = accountRepository.findByUserName(form.getRecepientUserName());
		Transaction transaction = new Transaction();
		Transaction saved = new Transaction();
		transaction.setSender(sender);
		transaction.setDescription(form.getDescription());
		transaction.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		transaction.setAmount(form.getAmount());
		transaction.setRecepient(recepient);
		saved = transactionRepository.save(transaction);
	    accountService.updateSender(saved);
	    accountService.updateRecepient(saved);

		return saved;
	}
	
	@Override
	public Page<Transaction> findTransactionsForPage(Pageable pageable, Long id) {
	    
		Page<Transaction> transactions = transactionRepository
				.findTransactionsBySenderIdOrRecepientId(id, id, pageable);
		transactions.forEach(t -> {
			if (t.getSender().getId().equals(id)) {
				t.setAmount(t.getAmount().negate());
			} 
		});
		return transactions;
	}

	@Override
	public List<ConnectionDTO> getConnectionsDTO(Account account) {
		
		return account.getConnections().stream().map(u -> new ConnectionDTO(u))
				.collect(Collectors.toList());
	}

	@Override
	public List<TransactionUserDTO> getTransactionsUserDTO(Page<Transaction> page) {

		return page.getContent().stream()
				.map(t -> new TransactionUserDTO(t)).collect(Collectors.toList());
	}
	
}
