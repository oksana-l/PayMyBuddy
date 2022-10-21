package com.PayMyBuddy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService{

	private TransactionRepository transactionRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		super();
		this.transactionRepository = transactionRepository;
	}
	
	public List<Transaction> findTransactionsBySenderId(Long senderId) {
		List<Transaction> transactions = transactionRepository.findTransactionsBySenderId(senderId);
		return transactions;
	}
	
}
