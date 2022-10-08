package com.PayMyBuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "transaction")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
 
	@Column(name = "id_connection")
	private int idConnection;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "amount")
	private long amount;
	
	public Transaction() {
		
	}

}
