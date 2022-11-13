package com.PayMyBuddy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PayMyBuddy.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	List<Transaction> findTransactionsBySenderId(Long id);
	List<Transaction> findTransactionsByRecepientId(Long id);
	Long findByRecepientUserName(String userName);
	List<Transaction> findTransactionsBySenderIdOrRecepientId(Long senderId, Long recepientId);
	Page<Transaction> findTransactionsBySenderIdOrRecepientId(Long senderId, 
			Long recepientId, Pageable pageable);
}
