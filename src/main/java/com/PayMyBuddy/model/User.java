package com.PayMyBuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
    @NotNull(message = "Email may not be null")
    @NotEmpty(message = "Email may not be null")
	@Column(name = "email")
	private String email;
	
    @NotNull(message = "Password may not be null")
    @NotEmpty(message = "Password may not be empty")
	@Column(name = "password")
	private String password;
	
	@Column(name = "balance")
	private float balance;
	
	public User() {
		
	}

	public User(String email, String password, float balance) {
		
		this.email = email;
		this.password = password;
		this.balance = balance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

}
