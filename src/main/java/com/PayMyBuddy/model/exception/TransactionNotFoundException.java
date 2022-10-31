package com.PayMyBuddy.model.exception;

import java.text.MessageFormat;

public class TransactionNotFoundException extends RuntimeException {
	
	public TransactionNotFoundException(Long id) {
		super(MessageFormat.format("Could not find transaction with id: {0}", id));
	}
}
