package com.betit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import javax.mail.MessagingException;

import com.betit.etc.ErrorHelper;
import com.betit.etc.SMPTEmailSender;

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
			// TODO send Error to Frontend
			e.printStackTrace();
			ErrorHelper.writeToErrorFile(e);
			final String exceptionText = ErrorHelper.getExceptionText(e);
			final String message = "" + new Date(System.currentTimeMillis()).toString() + System.lineSeparator() + exceptionText;
			try {
				SMPTEmailSender.sendMail(new String[] { "kai.jmueller@gmail.com" }, "Error in Backend", message);
			} catch (final MessagingException e1) {
				ErrorHelper.writeToErrorFile(e1);
			}
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
	public synchronized Connection getReaderConnection() throws SQLException {
		if (readerConnection == null || readerConnection.isClosed()) {
			readerConnection = DriverManager.getConnection(buildLoginUR(DatabaseUser.READ_USER));
		}
		// TODO isValid
		// TODO timeout
		return readerConnection;
	}

	/**
	 * 
	 * @return a connection to the database with the "writer" user
	 * @throws SQLException
	 */
	public synchronized Connection getWriterConnection() throws SQLException {
		if (writerConnection == null || writerConnection.isClosed()) {
			writerConnection = DriverManager.getConnection(buildLoginUR(DatabaseUser.WRITE_USER));
		}
		// TODO isValid
		// TODO commit
		// TODO timeout
		return writerConnection;
	}

	/**
	 * 
	 * @return a connection to the database with the "delete" user
	 * @throws SQLException
	 */
	public synchronized Connection getDeleteConnection() throws SQLException {
		if (deleteConnection == null || deleteConnection.isClosed()) {
			deleteConnection = DriverManager.getConnection(buildLoginUR(DatabaseUser.DELETE_USER));
		}
		// TODO isValid
		// TODO commit
		// TODO timeout
		return deleteConnection;
	}

	/**
	 * 
	 * @param databaseUser
	 *            the password and the username of a database user
	 * @return the login URL
	 */
	private String buildLoginUR(final DatabaseUser databaseUser) {
		final String mainPart = JDBC_PROTOCOLL + ":\\" + IP_OF_DATABASE + "/" + Constants.SCHEMA_NAME + "?";
		final String parameterPart = PARAMETER_USER + "=" + databaseUser.getUsername() + "&" + PARAMETER_PASSWORD + "=" + databaseUser.getPassword();
		return mainPart + parameterPart;
	}
}
