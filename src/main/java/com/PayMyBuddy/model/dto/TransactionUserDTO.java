package com.PayMyBuddy.model.dto;

import java.math.BigDecimal;

import com.PayMyBuddy.model.Transaction;

public class TransactionUserDTO {

	private String senderUserName;
	private String recepientUserName;
	private String date;
	private String description;
	private BigDecimal amount;

	public TransactionUserDTO() {

	}

	public TransactionUserDTO(Transaction transaction) {
		super();
		this.senderUserName = transaction.getSender().getUserName();
		this.recepientUserName = transaction.getRecepient().getUserName();
		this.date = transaction.getDate();
		this.description = transaction.getDescription();
		this.amount = transaction.getAmount();
	}

	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}

	public String getRecepientUserName() {
		return recepientUserName;
	}

	public void setRecepientUserName(String recepientUserName) {
		this.recepientUserName = recepientUserName;
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
