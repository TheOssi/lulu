package com.askit.exception;

public class DatabaseLayerException extends Exception {

	private static final long serialVersionUID = 1L;

	public DatabaseLayerException() {
		super();
	}

	public DatabaseLayerException(final String msg) {
		super(msg);
	}
}