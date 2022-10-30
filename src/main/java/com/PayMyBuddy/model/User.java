package com.PayMyBuddy.model;

import java.math.BigDecimal;
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

import com.PayMyBuddy.web.dto.UserDTO;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	
    @NotBlank(message = "Name may not be null")
    @Column(unique=true)
	private String userName;
    
    @NotBlank(message = "Email may not be null")
    @Column(unique=true)
	private String email;
	
    @NotBlank(message = "Password may not be null")
	private String password;
	
	private BigDecimal balance;
	
	@ManyToMany
	@JoinTable(name = "connection",
	   joinColumns = @JoinColumn(name = "user_id"), 	
	   inverseJoinColumns = {
			   @JoinColumn(name = "connected_user_id")
	   })
	private Set<User> connections;
	
	@OneToMany
	@JoinColumn(name = "sender_id")
	private List<Transaction> debits;
	
	@OneToMany
	@JoinColumn(name = "recepient_id")
	private List<Transaction> credits;
	
	public User() {
		
	}

	public User(@NotBlank(message = "Name may not be null") String userName,
			@NotBlank(message = "Email may not be null") String email,
			@NotBlank(message = "Password may not be null") String password, BigDecimal balance,
			Set<User> connections, List<Transaction> debits, List<Transaction> credits) {
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

	public Set<User> getConnections() {
		if (connections==null) {
			connections = new HashSet<>();
		}
		return connections;
	}

	public void setConnections(Set<User> connections) {
		this.connections = connections;
	}

	public List<Transaction> getDebits() {
		return debits;
	}

	public void setDebits(List<Transaction> debits) {
		this.debits = debits;
	}

	public List<Transaction> getCredits() {
		return credits;
	}

	public void setCredits(List<Transaction> credits) {
		this.credits = credits;
	}
	
	public void addDebit(Transaction t) {
		t.setSender(this);
		debits.add(t);
	}
	
	public void addCredit(Transaction t) {
		t.setRecepient(this);
		credits.add(t);
	}
	
    public void removeDebit(Transaction t){
        debits.remove(t);
    }
	
    public void removeCredit(Transaction t){
        credits.remove(t);
    }
    
    public static User from(UserDTO userDto){
    	User user = new User();
    	user.setUserName(userDto.getUserName());
        return user;
    }
 }
