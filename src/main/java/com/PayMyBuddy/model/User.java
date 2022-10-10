  package com.PayMyBuddy.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
    @NotNull(message = "Email may not be null")
    @NotEmpty(message = "Email may not be null")
	private String email;
	
    @NotNull(message = "Password may not be null")
    @NotEmpty(message = "Password may not be empty")
	private String password;
	
	private float balance;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection <Connection> connections;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection <Transaction> transactions;
	
	public User() {
		
	}

	public User(
			@NotNull(message = "Email may not be null") @NotEmpty(message = "Email may not be null") String email,
			@NotNull(message = "Password may not be null") @NotEmpty(message = "Password may not be empty") String password,
			float balance, Collection<Connection> connections) {
		super();
		this.email = email;
		this.password = password;
		this.balance = balance;
		this.connections = connections;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Collection<Connection> getConnections() {
		return connections;
	}

	public void setConnections(Collection<Connection> connections) {
		this.connections = connections;
	}

	public void addConnection(Connection connection) {
		connections.add(connection);
		connection.setUser(this);
	}
	
	public void removeConnection(Connection connection) {
		connections.remove(connection);
		connection.setUser(null);
	}

	public Collection<Transaction> getTransaction() {
		return transactions;
	}

	public void setTransaction(Collection<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
		transaction.setUser(this);
	}
	
	public void removeTransaction(Transaction transaction) {
		transactions.remove(transaction);
		transaction.setUser(null);
	}

}
