package com.PayMyBuddy.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private User sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recepient_id")
	private User recepient;
	
	private String date;
	private String description;
	private BigDecimal amount;

	public Transaction() {
		
	}

	public Transaction(User sender, User recepient, String date, 
			String description, BigDecimal amount) {
		super();
		this.sender = sender;
		this.recepient = recepient;
		this.date = date;
		this.description = description;
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

//    public Transaction from(TransactionFormDTO transactionDto){
//    	Transaction transaction = new Transaction();
//    	transaction.setSender(transactionDto.getSender());
//    	transaction.setRecepient(transactionDto.getRecepient());
//    	transaction.setDate(transactionDto.getDate());
//    	transaction.setDescription(transactionDto.getDescription());
//    	transaction.setAmount(transactionDto.getAmount());
//        return transaction;
//    }
}
