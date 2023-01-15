package com.PayMyBuddy.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "account")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	
    @NotBlank(message = "Name may not be null")
    @Column
	private String userName;
    
    @NotBlank(message = "Email may not be null")
    @Column(unique=true)
	private String email;
	
    @NotBlank(message = "Password may not be null")
	private String password;
	
	private BigDecimal balance;
	
	@ManyToMany
	@JoinTable(name = "connection",
	   joinColumns = @JoinColumn(name = "account_id"), 	
	   inverseJoinColumns = {
			   @JoinColumn(name = "connected_account_id")
	   })
	private Set<Account> connections;
	
	@OneToMany(mappedBy = "sender")
	private List<Transaction> debits;
	
	@OneToMany(mappedBy = "recepient")
	private List<Transaction> credits;
	
	public Account() {
		
	}

	public Account(@NotBlank(message = "Name may not be null") String userName,
			@NotBlank(message = "Email may not be null") String email,
			@NotBlank(message = "Password may not be null") String password, BigDecimal balance,
			Set<Account> connections, List<Transaction> debits, List<Transaction> credits) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.balance = balance;
		this.connections = connections;
		this.debits = debits;
		this.credits = credits;
	}

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Set<Account> getConnections() {
		if (connections==null) {
			connections = new HashSet<>();
		}
		return connections;
	}

	public void setConnections(Set<Account> connections) {
		this.connections = connections;
	}

	public List<Transaction> getDebits() {
		if (debits==null) {
			debits = new ArrayList<>();
		}
		return debits;
	}

	public void setDebits(List<Transaction> debits) {
		this.debits = debits;
	}

	public List<Transaction> getCredits() {
		if (credits==null) {
			credits = new ArrayList<>();
		}
		return credits;
	}

	public void setCredits(List<Transaction> credits) {
		this.credits = credits;
	}

 }
