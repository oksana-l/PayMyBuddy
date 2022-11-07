package com.PayMyBuddy.model.dto;

public class UserForTransactionDTO {

	private String userName;

	public UserForTransactionDTO() {

	}
	public UserForTransactionDTO(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
