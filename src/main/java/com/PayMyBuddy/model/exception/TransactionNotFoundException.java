package com.PayMyBuddy.model.exception;

import java.text.MessageFormat;

public class TransactionNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public TransactionNotFoundException(Long id) {
		super(MessageFormat.format("Could not find transaction with id: {0}", id));
	}
}
