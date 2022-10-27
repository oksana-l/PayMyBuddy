package com.PayMyBuddy.web.dto;

import java.math.BigDecimal;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.repository.UserRepository;

public class TransactionDTO {
	
	private Long userId;
	private String connectedUserName;
	private String date;
	private String description;
	private BigDecimal amount;
	
	public TransactionDTO() {

	}
	
	public TransactionDTO(Transaction transaction, UserRepository userRepository, String description) {
		this.userId = transaction.getId();
		this.connectedUserName = userRepository.findById(transaction.getId()).get().getUserName();
		this.description = description;
		this.date = transaction.getDate();
		this.amount = transaction.getAmount();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getConnectedUserName() {
		return connectedUserName;
	}

	public void setConnectedUserName(String connectedUserName) {
		this.connectedUserName = connectedUserName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
