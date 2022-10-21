package com.PayMyBuddy.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "connection")
@IdClass(ConnectionId.class)
public class Connection implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private Long userId;
	
	@Id
	@Column(name = "connected_user_id")
	private Long connectedUserId;
	
	public Connection() {
		
	}

	public Connection(Long userId, Long connectedUserId) {
		super();
		this.userId = userId;
		this.connectedUserId = connectedUserId;
	}

	@ManyToMany(mappedBy = "connections")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@ManyToMany(mappedBy = "connections")
	public Long getConnectedUserId() {
		return connectedUserId;
	}

	public void setConnectedUserId(Long connectedUserId) {
		this.connectedUserId = connectedUserId;
	}


}
