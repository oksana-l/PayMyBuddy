package com.PayMyBuddy.model.dto;

import java.util.Objects;

import com.PayMyBuddy.model.Account;

public class ConnectionDTO {

	private String userName;
	private String email;
	
	public ConnectionDTO() {
		
	}
	
	public ConnectionDTO(Account account) {
		this.userName = account.getUserName();
		this.email = account.getEmail();
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectionDTO other = (ConnectionDTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(userName, other.userName);
	}
}
