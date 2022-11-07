package com.PayMyBuddy.model.dto;

import java.math.BigDecimal;

import com.PayMyBuddy.model.Transaction;

public class TransactionDTO {
	
	private UserForTransactionDTO sender;
	private UserForTransactionDTO recepient;
	private String date;
	private String description;
	private BigDecimal amount;
	
	public TransactionDTO() {

	}
	
	public TransactionDTO(Transaction transaction, String description) {
		this.sender = new UserForTransactionDTO(transaction.getSender().getUserName());
		this.recepient = new UserForTransactionDTO(transaction.getRecepient().getUserName());
		this.description = description;
		this.date = transaction.getDate();
		this.amount = transaction.getAmount();
	}

	public UserForTransactionDTO getSender() {
		return sender;
	}

	public void setSender(UserForTransactionDTO sender) {
		this.sender = sender;
	}

	public UserForTransactionDTO getRecepient() {
		return recepient;
	}

	public void setRecepient(UserForTransactionDTO recepient) {
		this.recepient = recepient;
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
