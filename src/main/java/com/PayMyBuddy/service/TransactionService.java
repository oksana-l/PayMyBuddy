package com.PayMyBuddy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.dto.TransactionFormDTO;

public interface TransactionService {


	Transaction getTransaction(Long id);
	
	Transaction save(String senderEmail, TransactionFormDTO form);

	List<Transaction> findTransactionsByUser(Long id);

	Long getId(TransactionFormDTO form);

	Page<Transaction> findTransactionWithSorting(Pageable pageable, Long id);

	Page<Transaction> findPage(int pageNumber, Long id);

}
