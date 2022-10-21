package com.PayMyBuddy.web.dto;

import com.PayMyBuddy.model.Transaction;

public class TransactionDTO {
	
	private String connectedUserName;
	private String date;
	private String description;
	private float amount;
	
	public TransactionDTO() {

	}
	
	public TransactionDTO(Transaction transaction) {
		this.connectedUserName = transaction.getRecepient().getUserName();
		this.description = transaction.getDescription();
		this.date = transaction.getDate();
		this.amount = transaction.getAmount();
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

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
}
