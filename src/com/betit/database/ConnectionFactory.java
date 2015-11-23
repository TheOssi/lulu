package com.betit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import javax.mail.MessagingException;

import com.betit.etc.ErrorHelper;
import com.betit.etc.SMPTEmailSender;
import com.betit.etc.Util;

public class ConnectionFactory {
	private final static String JDBC_PROTOCOLL = "jdbc:mysql";
	private final static String PARAMETER_USER = "user";
	private final static String PARAMETER_PASSWORD = "password";
	private static final String IP_OF_DATABASE = "localhost";
	private static ConnectionFactory INSTANCE;

	private Connection readerConnection;
	private Connection writerConnection;
	private Connection deleteConnection;

	private ConnectionFactory() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (final ClassNotFoundException e) {
			handleError(e);
		}
	}

	/**
	 * This Instance handle all the Connections to the Database
	 * 
	 * @return the instance of the ConnectionFactory
	 */
	public static synchronized ConnectionFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ConnectionFactory();
		}
		return INSTANCE;
	}

	/**
	 * 
	 * @return a connection to the database with the "reader" user
	 * @throws SQLException
	 */
	public synchronized Connection getReaderConnection() {
		try {
			if (readerConnection == null || readerConnection.isClosed()) {
				readerConnection = DriverManager.getConnection(buildLoginUR(DatabaseUser.READ_USER));
			}
			// TODO timeout
			return readerConnection;
		} catch (final SQLException e) {
			handleError(e);
			return null;
		}
	}

	/**
	 * 
	 * @return a connection to the database with the "writer" user
	 * @throws SQLException
	 */
	public synchronized Connection getWriterConnection() {
		try {
			if (writerConnection == null || writerConnection.isClosed()) {
				writerConnection = DriverManager.getConnection(buildLoginUR(DatabaseUser.WRITE_USER));
			}
			// TODO commit
			// TODO timeout
			return writerConnection;
		} catch (final SQLException e) {
			handleError(e);
			return null;
		}
	}

	/**
	 * 
	 * @return a connection to the database with the "delete" user
	 * @throws SQLException
	 */
	public synchronized Connection getDeleteConnection() {
		try {
			if (deleteConnection == null || deleteConnection.isClosed()) {
				deleteConnection = DriverManager.getConnection(buildLoginUR(DatabaseUser.DELETE_USER));
			}
			// TODO commit
			// TODO timeout
			return deleteConnection;
		} catch (final SQLException e) {
			handleError(e);
			return null;
		}
	}

	/**
	 * 
	 * @param databaseUser
	 *            the password and the username of a database user
	 * @return the login URL
	 */
	private String buildLoginUR(final DatabaseUser databaseUser) {
		final String mainPart = JDBC_PROTOCOLL + "://" + IP_OF_DATABASE + "/" + Constants.SCHEMA_NAME + "?";
		final String parameterPart = PARAMETER_USER + "=" + databaseUser.getUsername() + "&" + PARAMETER_PASSWORD + "=" + databaseUser.getPassword();
		return mainPart + parameterPart;
	}

	private void handleError(final Exception exception) {
		// TODO send Error to Frontend
		exception.printStackTrace();
		ErrorHelper.writeToErrorFile(exception);
		final String exceptionText = Util.getExceptionText(exception);
		final String message = "" + new Date(System.currentTimeMillis()).toString() + System.lineSeparator() + exceptionText;
		try {
			SMPTEmailSender.sendMail(new String[] { "kai.jmueller@gmail.com" }, exception.getMessage(), message);
		} catch (final MessagingException e1) {
			ErrorHelper.writeToErrorFile(e1);
		}
	}
}
