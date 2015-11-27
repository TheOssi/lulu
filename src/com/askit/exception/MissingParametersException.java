package com.askit.exception;

public class MissingParametersException extends Exception {

	private static final long serialVersionUID = 1L;

	public MissingParametersException() {
		super();
	}

	public MissingParametersException(final String msg) {
		super(msg);
	}
}
