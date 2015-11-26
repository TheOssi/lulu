package com.askit.exception;

public class DuplicateHashException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateHashException() {
		super();
	}

	public DuplicateHashException(final String msg) {
		super(msg);
	}
}
