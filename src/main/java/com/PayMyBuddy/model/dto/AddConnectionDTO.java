package com.PayMyBuddy.model.dto;

import com.PayMyBuddy.model.Account;

public class AddConnectionDTO {

	private String email;
	
	public AddConnectionDTO() {
		
	}
	
	public AddConnectionDTO(Account account) {
		this.email = account.getEmail();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
