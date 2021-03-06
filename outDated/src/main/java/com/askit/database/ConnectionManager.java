package com.askit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.askit.etc.Constants;
import com.askit.exception.DriverNotFoundException;

public class ConnectionManager {
	private final static String JDBC_PROTOCOLL = "jdbc:mysql";
	private final static String PARAMETER_USER = "user";
	private final static String PARAMETER_PASSWORD = "password";
	private static final String IP_OF_DATABASE = "localhost";
	private static final String MARIA_DB_DRIVER = "org.mariadb.jdbc.Driver";
	private static ConnectionManager instance;

	private Connection readerConnection;
	private Connection writerConnection;
	private Connection deleteConnection;

	private ConnectionManager() throws DriverNotFoundException {
		try {
			Class.forName(MARIA_DB_DRIVER);
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
			throw new DriverNotFoundException("Driver class not found");
		}
	}

	/**
	 * This instance handles all connections to the database
	 *
	 * @return the instance of the ConnectionFactory
	 * @throws DriverNotFoundException
	 *             if the driver wasn't found
	 */
	public static synchronized ConnectionManager getInstance() throws DriverNotFoundException {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}

	/**
	 *
	 * @return a connection to the database with the "reader" user
	 * @throws SQLException
	 */
	public synchronized Connection getReaderConnection() throws SQLException {
		if (readerConnection == null || readerConnection.isClosed()) {
			readerConnection = DriverManager.getConnection(buildLoginUR(DatabaseUser.READ_USER));
			readerConnection.setAutoCommit(true);
			readerConnection.setReadOnly(true);
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
			writerConnection = DriverManager.getConnection(buildLoginUR(DatabaseUser.WRITE_USER));
			writerConnection.setAutoCommit(true);
		}
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
			deleteConnection.setAutoCommit(true);
		}
		// TODO timeout
		return deleteConnection;
	}

	public Connection buildOneTimeConnection(final String username, final String password) throws SQLException {
		final Connection connection = DriverManager.getConnection(buildLoginURL(username, password));
		return connection;
	}

	/**
	 *
	 * @param databaseUser
	 *            the password and the username of a database user
	 * @return the login URL
	 */
	private String buildLoginUR(final DatabaseUser databaseUser) {
		return buildLoginURL(databaseUser.getUsername(), databaseUser.getPassword());
	}

	private String buildLoginURL(final String username, final String password) {
		final String mainPart = JDBC_PROTOCOLL + "://" + IP_OF_DATABASE + "/" + Constants.SCHEMA_NAME + "?";
		final String parameterPart = PARAMETER_USER + "=" + username + "&" + PARAMETER_PASSWORD + "=" + password;
		return mainPart + parameterPart;
	}
}
