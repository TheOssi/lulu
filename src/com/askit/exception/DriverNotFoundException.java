package com.askit.exception;

public class DriverNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public DriverNotFoundException() {
		super();
	}

	public DriverNotFoundException(final String msg) {
		super(msg);
	}
}
