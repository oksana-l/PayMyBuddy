package com.PayMyBuddy.model.dto;

import com.PayMyBuddy.model.Account;

public class AccountDTO {
	
	private String userName;
	
	private String email;
	
	private String password;
	
	public AccountDTO() {
	
	}

	public AccountDTO(Account account) {
		this.userName = account.getUserName();
		this.email = account.getEmail();
		this.password = account.getPassword();
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
}
