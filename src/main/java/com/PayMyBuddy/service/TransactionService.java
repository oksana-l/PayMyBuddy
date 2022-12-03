package com.PayMyBuddy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.dto.TransactionFormDTO;

public interface TransactionService {


	Transaction getTransaction(Long id);

	Transaction saveTransaction(String senderEmail, TransactionFormDTO form);

	Page<Transaction> findTransactionWithSorting(Pageable pageable, Long id);

}
