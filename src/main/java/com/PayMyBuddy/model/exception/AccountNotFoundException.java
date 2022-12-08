package com.PayMyBuddy.model.exception;

import java.text.MessageFormat;

public class AccountNotFoundException extends RuntimeException {
	
	public AccountNotFoundException(Long id) {
		super(MessageFormat.format("Could not find user with id: {0}", id));
	}

}
