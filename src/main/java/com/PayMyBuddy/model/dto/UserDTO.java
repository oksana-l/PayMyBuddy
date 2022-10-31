package com.PayMyBuddy.model.dto;

import com.PayMyBuddy.model.User;

public class UserDTO {
	
	private String userName;
	
	private String email;
	
	private String password;
	
	public UserDTO() {
	
	}

	public UserDTO(User user) {
		this.userName = user.getUserName();
		this.email = user.getEmail();
		this.password = user.getPassword();
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
