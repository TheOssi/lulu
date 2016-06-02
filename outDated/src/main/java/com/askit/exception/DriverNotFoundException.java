package com.askit.exception;

/**
 * The DriverNotFoundException appears if the database driver wasn't found
 * 
 * @author Kai Müller
 * @version 1.0
 * @since 1.0
 */
public class DriverNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with null as its detail message. The cause is
	 * not initialized, and may subsequently be initialized by a call to
	 * {@link java.lang.Throwable#initCause(Throwable)} .
	 * 
	 * @see java.lang.Exception#Exception()
	 */
	public DriverNotFoundException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message. The cause
	 * is not initialized, and may subsequently be initialized by a call to
	 * {@link Throwable.initCause(Throwable)}.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link Throwable#getMessage()} method.
	 * 
	 * @see Exception#Exception(String)
	 */
	public DriverNotFoundException(final String msg) {
		super(msg);
	}
}