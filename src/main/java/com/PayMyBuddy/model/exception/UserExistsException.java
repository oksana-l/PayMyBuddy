package com.PayMyBuddy.model.exception;

public class UserExistsException extends Exception{

	private static final long serialVersionUID = 1L;
	private String userName;
	private String email;
	
	public UserExistsException(final String userName, final String email, final String message) {
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
