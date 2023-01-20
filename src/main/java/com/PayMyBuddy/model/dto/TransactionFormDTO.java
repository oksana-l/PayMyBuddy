package com.PayMyBuddy.model.dto;

import java.math.BigDecimal;

public class TransactionFormDTO {
	
	private String recepientUserName;
	private String description;
	private BigDecimal amount = BigDecimal.ZERO;

	public String getRecepientUserName() {
		return recepientUserName;
	}

	public void setRecepientUserName(String recepientUserName) {
		this.recepientUserName = recepientUserName;
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
