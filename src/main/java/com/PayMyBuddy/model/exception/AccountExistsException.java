package com.PayMyBuddy.model.exception;

import org.springframework.http.HttpStatus;

public class AccountExistsException extends Exception{

	private static final long serialVersionUID = 1L;
	private String userName;
	private String email;
	
	public AccountExistsException() {

	}
	
	public AccountExistsException(HttpStatus badRequest, final String userName, 
			final String email, final String message) {
		super(message);
		this.setUserName(userName);
		this.setEmail(email);
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

}
