package com.PayMyBuddy.model;

import java.io.Serializable;

public class ConnectionId  implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long userId;
	private Long connectedUserId;
	
	public ConnectionId() {

	}
	
	public ConnectionId(Long userId, Long connectedUserId) {
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
	
	
}
