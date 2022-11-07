package com.PayMyBuddy.model.dto;

import java.util.List;

public class UserForConnectionDTO {

	String userName;
	String userEmail;
	List<UserForConnectionDTO> connections;
	
	public UserForConnectionDTO() {

	}
	
	public UserForConnectionDTO(String userName, String userEmail, List<UserForConnectionDTO> connections) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.connections = connections;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<UserForConnectionDTO> getConnections() {
		return connections;
	}

	public void setConnections(List<UserForConnectionDTO> connections) {
		this.connections = connections;
	}
	

}
