package com.PayMyBuddy.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "connection")
public class Connection {

	@Id
	@Column(name = "user_id")
	private Long userId;
	
	
	@Column(name = "connected_user_id")
	private Long connectedUserId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user")
	private User user;
	
	public Connection() {
		
	}

	public Connection(Long userId, Long connectedUserId) {
		super();
		this.userId = userId;
		this.connectedUserId = connectedUserId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getConnectedUserId() {
		return connectedUserId;
	}

	public void setConnectedUserId(Long connectedUserId) {
		this.connectedUserId = connectedUserId;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
