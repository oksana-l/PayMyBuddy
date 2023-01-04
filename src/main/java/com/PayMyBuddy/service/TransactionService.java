package com.PayMyBuddy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.dto.TransactionFormDTO;

public interface TransactionService {

	Transaction saveTransaction(String senderEmail, TransactionFormDTO form);

	Page<Transaction> findTransactionsForPage(Pageable pageable, Long id);

}
