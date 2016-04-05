package com.askit.exception;

public class WrongHashException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongHashException() {
		super();
	}

	public WrongHashException(final String msg) {
		super(msg);
	}
}
