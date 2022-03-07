package com.banking.assignment.exceptions;

public class ConcurrentLoginException extends RuntimeException {

	public ConcurrentLoginException(String user) {
		super(user + " is not allowed to do multiple logins");
	}
}