package com.askit.exception;

/**
 * This exception is thrown if a duplicate hash appears.
 * 
 * @author Max Lenk
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class DuplicateHashException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with null as its detail message. The cause is
	 * not initialized, and may subsequently be initialized by a call to
	 * {@link java.lang.Throwable#initCause(Throwable)} .
	 * 
	 * @see java.lang.Exception#Exception()
	 */
	public DuplicateHashException() {
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
	public DuplicateHashException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message
	 * of (cause==null ? null : cause.toString()) (which typically contains the
	 * class and detail message of cause). This constructor is useful for
	 * exceptions that are little more than wrappers for other throwables (for
	 * example, PrivilegedActionException).
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link Throwable#getCause()} method). (A null value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 * 
	 * @see Exception#Exception(Throwable)
	 */
	public DuplicateHashException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * Note that the detail message associated with cause is not automatically
	 * incorporated in this exception's detail message.
	 * 
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            {@link Throwable#getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link Throwable#getCause()} method). (A null value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 * 
	 * @see Exception#Exception(String, Throwable)
	 */
	public DuplicateHashException(final String message, final Throwable cause) {
		super(message, cause);
	}
}