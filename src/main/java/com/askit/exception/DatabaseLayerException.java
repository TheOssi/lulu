package com.askit.exception;

public class DatabaseLayerException extends Exception {

	private static final long serialVersionUID = 1L;

	public DatabaseLayerException() {
	}

	public DatabaseLayerException(final String message) {
		super(message);
	}

	public DatabaseLayerException(final Throwable cause) {
		super(cause);
	}

	public DatabaseLayerException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DatabaseLayerException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}