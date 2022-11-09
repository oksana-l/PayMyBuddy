package com.PayMyBuddy.model.dto;

import java.util.List;

public class TransactionUserDTO {

	private int id;
	private List<String> connections;

	public TransactionUserDTO() {

	}

	public TransactionUserDTO(int id, List<String> connections) {
		super();
		this.id = id;
		this.connections = connections;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<String> getConnections() {
		return connections;
	}

	public void setConnections(List<String> connections) {
		this.connections = connections;
	}
	
}
