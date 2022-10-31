package com.PayMyBuddy.model.dto;

import java.math.BigDecimal;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.User;

public class TransactionDTO {
	
	private User sender;
	private User recepient;
	private String date;
	private String description;
	private BigDecimal amount;
	
	public TransactionDTO() {

	}
	
	public TransactionDTO(Transaction transaction, String description) {
		this.sender = transaction.getRecepient();
		this.recepient = transaction.getRecepient();
		this.description = description;
		this.date = transaction.getDate();
		this.amount = transaction.getAmount();
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getRecepient() {
		return recepient;
	}

	public void setRecepient(User recepient) {
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
