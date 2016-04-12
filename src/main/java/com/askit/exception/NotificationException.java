package com.askit.exception;

public class NotificationException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotificationException() {
		super();
	}

	public NotificationException(final String msg) {
		super(msg);
	}

	public NotificationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public NotificationException(final Throwable cause) {
		super(cause);
	}
}
