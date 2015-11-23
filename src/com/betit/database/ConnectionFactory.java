package com.betit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.betit.exception.DriverNotFoundException;

public class ConnectionFactory {
	private final static String JDBC_PROTOCOLL = "jdbc:mysql";
	private final static String PARAMETER_USER = "user";
	private final static String PARAMETER_PASSWORD = "password";
	private static final String IP_OF_DATABASE = "localhost";
	private static ConnectionFactory INSTANCE;

	private Connection readerConnection;
	private Connection writerConnection;
	private Connection deleteConnection;

	private ConnectionFactory() throws DriverNotFoundException {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
			throw new DriverNotFoundException("Driver class not found");
		}
	}

	/**
	 * This Instance handle all the Connections to the Database
	 *
	 * @return the instance of the ConnectionFactory
	 * @throws DriverNotFoundException
	 */
	public static synchronized ConnectionFactory getInstance()
			throws DriverNotFoundException {
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
			readerConnection = DriverManager
					.getConnection(buildLoginUR(DatabaseUser.READ_USER));
		}
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
			writerConnection = DriverManager
					.getConnection(buildLoginUR(DatabaseUser.WRITE_USER));
		}
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
			deleteConnection = DriverManager
					.getConnection(buildLoginUR(DatabaseUser.DELETE_USER));
		}
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
		final String mainPart = JDBC_PROTOCOLL + "://" + IP_OF_DATABASE + "/"
				+ Constants.SCHEMA_NAME + "?";
		final String parameterPart = PARAMETER_USER + "="
				+ databaseUser.getUsername() + "&" + PARAMETER_PASSWORD + "="
				+ databaseUser.getPassword();
		return mainPart + parameterPart;
	}
}
