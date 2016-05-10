package com.askit.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

import com.askit.database.sqlHelper.Constants;

public class ConnectionManager {
	private static final int MAX_IDLE = 0;
	private static final int MIN_IDLE = 0;
	private static final int MAX_PARALLEL_CONNECTIONS = 100;
	private static final int INITIAL_IDLE_CONNECTIONS = 5;
	private static final int MAX_PARALLEL_PREPARED_STATEMENTS = 100;
	private static final int LOGIN_TIMEOUT = 10000;
	private static final int QUERY_TIMEOUT = 10000;
	private static final int MAX_CONNECTION_LIFETIME = 10000;
	private static final String MARIA_DB_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String IP_OF_DATABASE = "localhost";
	private static final String JDBC_PROTOCOLL = "jdbc:mysql";
	private static final String URL = JDBC_PROTOCOLL + "://" + IP_OF_DATABASE + "/" + Constants.SCHEMA_NAME;
	private static final ConnectionManager INSTANCE = new ConnectionManager();

	private BasicDataSource readerDataSource;
	private BasicDataSource writerDataSource;
	private BasicDataSource deleterDataSource;

	private ConnectionManager() {
		setDeleterDataSource();
		setReaderDataSource();
		setWriterDataSource();
	}

	private void setReaderDataSource() {
		readerDataSource = new BasicDataSource();
		setDefaultSettings(readerDataSource);
		readerDataSource.setUsername(DatabaseUser.READ_USER.getUsername());
		readerDataSource.setPassword(DatabaseUser.READ_USER.getPassword());
		readerDataSource.setDefaultReadOnly(true);
	}

	private void setWriterDataSource() {
		writerDataSource = new BasicDataSource();
		setDefaultSettings(writerDataSource);
		writerDataSource.setUsername(DatabaseUser.WRITE_USER.getUsername());
		writerDataSource.setPassword(DatabaseUser.WRITE_USER.getPassword());
		writerDataSource.setDefaultReadOnly(false);
	}

	private void setDeleterDataSource() {
		deleterDataSource = new BasicDataSource();
		setDefaultSettings(deleterDataSource);
		deleterDataSource.setUsername(DatabaseUser.DELETE_USER.getUsername());
		deleterDataSource.setPassword(DatabaseUser.DELETE_USER.getPassword());
		deleterDataSource.setDefaultAutoCommit(true);
	}

	private void setDefaultSettings(final BasicDataSource basicDataSource) {
		basicDataSource.setDriverClassName(MARIA_DB_DRIVER);
		basicDataSource.setUrl(URL);
		basicDataSource.setDefaultAutoCommit(true);
		basicDataSource.setMinIdle(MIN_IDLE);
		basicDataSource.setMaxIdle(MAX_IDLE);
		basicDataSource.setMaxTotal(MAX_PARALLEL_CONNECTIONS);
		basicDataSource.setInitialSize(INITIAL_IDLE_CONNECTIONS);
		basicDataSource.setMaxOpenPreparedStatements(MAX_PARALLEL_PREPARED_STATEMENTS);
		basicDataSource.setDefaultQueryTimeout(QUERY_TIMEOUT);
		basicDataSource.setMaxConnLifetimeMillis(MAX_CONNECTION_LIFETIME);
		tryToSetLoginTimeout(basicDataSource);
	}

	private void tryToSetLoginTimeout(final BasicDataSource basicDataSource) {
		try {
			basicDataSource.setLoginTimeout(LOGIN_TIMEOUT);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	public static synchronized ConnectionManager getInstance() {
		return INSTANCE;
	}

	/**
	 *
	 * @return a connection to the database with the "reader" user
	 * @throws SQLException
	 */
	public synchronized Connection getReaderConnection() throws SQLException {
		return readerDataSource.getConnection();
	}

	/**
	 *
	 * @return a connection to the database with the "writer" user
	 * @throws SQLException
	 */
	public synchronized Connection getWriterConnection() throws SQLException {
		return writerDataSource.getConnection();
	}

	/**
	 *
	 * @return a connection to the database with the "delete" user
	 * @throws SQLException
	 */
	public synchronized Connection getDeleteConnection() throws SQLException {
		return deleterDataSource.getConnection();
	}
}