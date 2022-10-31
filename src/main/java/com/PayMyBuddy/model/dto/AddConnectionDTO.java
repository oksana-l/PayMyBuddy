package com.PayMyBuddy.model.dto;

public class AddConnectionDTO {
	
	private String email;
	
	public AddConnectionDTO() {
		
	}
	
	public AddConnectionDTO( String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
