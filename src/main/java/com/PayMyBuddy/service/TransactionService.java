package com.PayMyBuddy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.dto.ConnectionDTO;
import com.PayMyBuddy.model.dto.TransactionFormDTO;
import com.PayMyBuddy.model.dto.TransactionUserDTO;

public interface TransactionService {

	Transaction saveTransaction(String senderEmail, TransactionFormDTO form);

	Page<Transaction> findTransactionsForPage(Pageable pageable, Long id);

	List<ConnectionDTO> getConnectionsDTO(Account account);

	List<TransactionUserDTO> getTransactionsUserDTO(Page<Transaction> page);

}
