package com.PayMyBuddy.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionUserDTO other = (TransactionUserDTO) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(date, other.date)
				&& Objects.equals(description, other.description)
				&& Objects.equals(recepientUserName, other.recepientUserName)
				&& Objects.equals(senderUserName, other.senderUserName);
	}

	
}
